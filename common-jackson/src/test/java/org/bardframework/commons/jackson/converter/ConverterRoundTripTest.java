package org.bardframework.commons.jackson.converter;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import tools.jackson.databind.ObjectMapper;
import tools.jackson.databind.annotation.JsonDeserialize;
import tools.jackson.databind.annotation.JsonSerialize;
import tools.jackson.databind.json.JsonMapper;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * این تست‌ها قرارداد سیم (wire) را می‌سنجند: تاریخ‌ها به صورت عدد رد و بدل می‌شوند و متن ورودی
 * پیش از رسیدن به سرویس پاک‌سازی می‌شود.
 */
class ConverterRoundTripTest {

    private final ObjectMapper mapper = JsonMapper.builder().build();

    public static class DateHolder {
        @JsonProperty("date")
        @JsonSerialize(using = LocalDateToLongConverter.class)
        @JsonDeserialize(using = LongToLocalDateConverter.class)
        public LocalDate date;

        @JsonProperty("dateTime")
        @JsonSerialize(using = LocalDateTimeToLongConverter.class)
        @JsonDeserialize(using = LongToLocalDateTimeConverter.class)
        public LocalDateTime dateTime;
    }

    public static class TextHolder {
        @JsonProperty("title")
        @JsonDeserialize(using = JsonRloRemoverHtmlScapeDeserializer.class)
        public String title;
    }

    @Test
    @DisplayName("a date is written as epoch millis, not as an ISO string")
    void localDateIsSerialisedAsNumber() {
        DateHolder holder = new DateHolder();
        holder.date = LocalDate.of(2026, 9, 8);

        String json = mapper.writeValueAsString(holder);

        assertThat(json).contains("\"date\":").doesNotContain("2026-09-08");
    }

    @Test
    void localDateRoundTrips() {
        DateHolder holder = new DateHolder();
        holder.date = LocalDate.of(2026, 9, 8);

        DateHolder restored = mapper.readValue(mapper.writeValueAsString(holder), DateHolder.class);

        assertThat(restored.date).isEqualTo(holder.date);
    }

    @Test
    void localDateTimeRoundTrips() {
        DateHolder holder = new DateHolder();
        holder.dateTime = LocalDateTime.of(2026, 9, 8, 14, 30, 15);

        DateHolder restored = mapper.readValue(mapper.writeValueAsString(holder), DateHolder.class);

        assertThat(restored.dateTime).isEqualTo(holder.dateTime);
    }

    @Test
    void nullDateIsWrittenAsNull() {
        assertThat(mapper.writeValueAsString(new DateHolder())).contains("null");
    }

    @Test
    @DisplayName("bidi override characters are neutralised so stored text cannot lie about its content")
    void bidiOverrideCharactersAreRemoved() {
        TextHolder holder = mapper.readValue("{\"title\":\"a\\u202Eb\"}", TextHolder.class);

        assertThat(holder.title).doesNotContain("\u202E");
    }

    @Test
    @DisplayName("markup is passed through unchanged — despite its name this deserializer does not HTML-escape")
    void markupIsNotEscaped() {
        TextHolder holder = mapper.readValue("{\"title\":\"<b>hi</b>\"}", TextHolder.class);

        assertThat(holder.title).contains("<b>");
    }

    @Test
    void removeRloIsIdempotent() {
        String once = JsonRloRemoverHtmlScapeDeserializer.removeRlo("a\u202Eb");
        assertThat(JsonRloRemoverHtmlScapeDeserializer.removeRlo(once)).isEqualTo(once);
    }

    @Test
    void plainTextPassesThroughUnchanged() {
        TextHolder holder = mapper.readValue("{\"title\":\"سلام\"}", TextHolder.class);

        assertThat(holder.title).isEqualTo("سلام");
    }
}
