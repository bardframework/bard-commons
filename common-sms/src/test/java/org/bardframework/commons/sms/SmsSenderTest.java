package org.bardframework.commons.sms;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

import static org.assertj.core.api.Assertions.assertThat;

class SmsSenderTest {

    /**
     * فرستنده‌ی آزمایشی که آخرین آرگومان‌های دریافتی را نگه می‌دارد.
     */
    private static class RecordingSmsSender implements SmsSender {
        private final AtomicReference<Map<String, Object>> lastArgs = new AtomicReference<>();
        private final boolean result;

        RecordingSmsSender(boolean result) {
            this.result = result;
        }

        @Override
        public boolean send(Map<String, Object> args) {
            lastArgs.set(args);
            return result;
        }
    }

    @Test
    @DisplayName("the convenience overload puts 'to' and 'message' into the argument map")
    void send_populatesToAndMessageKeys() throws IOException {
        RecordingSmsSender sender = new RecordingSmsSender(true);

        sender.send("09120000000", "hello", Map.of("sign", "bard"));

        assertThat(sender.lastArgs.get())
                .containsEntry(SmsSender.TO_KEY, "09120000000")
                .containsEntry(SmsSender.MESSAGE_KEY, "hello")
                .containsEntry("sign", "bard");
    }

    @Test
    @DisplayName("the caller's map is not mutated")
    void send_doesNotMutateGivenArgs() throws IOException {
        RecordingSmsSender sender = new RecordingSmsSender(true);
        Map<String, Object> args = Map.of("sign", "bard");

        sender.send("09120000000", "hello", args);

        assertThat(args).hasSize(1).containsOnlyKeys("sign");
    }

    @Test
    void noOpSender_reportsSuccessWithoutSending() throws IOException {
        assertThat(new SmsSenderNoOp().send(Map.of())).isTrue();
    }

    @Test
    @DisplayName("the ranked sender delegates to exactly one sender, chosen by the comparator")
    void rankedSender_delegatesToTheTopRankedSender() throws IOException {
        RecordingSmsSender cheap = new RecordingSmsSender(true);
        RecordingSmsSender expensive = new RecordingSmsSender(true);
        List<SmsSender> order = new ArrayList<>(List.of(expensive, cheap));

        SmsSenderRanked ranked = new SmsSenderRanked(order) {
            @Override
            protected Comparator<SmsSender> comparator() {
                /* آخرین عنصر پس از مرتب‌سازی انتخاب می‌شود؛ پس ارزان‌ترین را آخر می‌گذاریم */
                return Comparator.comparingInt(sender -> sender == cheap ? 1 : 0);
            }
        };

        assertThat(ranked.send(Map.of(SmsSender.TO_KEY, "09120000000"))).isTrue();
        assertThat(cheap.lastArgs.get()).isNotNull();
        assertThat(expensive.lastArgs.get()).isNull();
    }

    @Test
    void rankedSender_exposesItsSenders() {
        RecordingSmsSender one = new RecordingSmsSender(true);
        assertThat(new SmsSenderRanked(List.of(one)).getSenders()).containsExactly(one);
    }
}
