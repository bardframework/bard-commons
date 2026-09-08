package org.bardframework.commons.utils;

import lombok.Getter;
import lombok.Setter;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * تشخیص نوع جنریک در زمان اجرا، ستون فقرات کلاس‌های پایه‌ی برد است؛ شکستن آن یعنی شکستن کل فریمورک.
 */
class ReflectionUtilsTest {

    @Getter
    @Setter
    public static class Company {
        private String title;
    }

    @Getter
    @Setter
    public static class Owner {
        private String name;
        private Company company;
    }

    @Getter
    @Setter
    public static class Product {
        private String code;
        private Owner owner;
    }

    static abstract class GenericBase<M, C> {
    }

    static class ConcreteChild extends GenericBase<Product, Owner> {
    }

    static class GrandChild extends ConcreteChild {
    }

    @Nested
    class GenericArgType {

        @Test
        void resolvesEachTypeArgumentByIndex() {
            assertThat(ReflectionUtils.<Product>getGenericArgType(ConcreteChild.class, 0)).isEqualTo(Product.class);
            assertThat(ReflectionUtils.<Owner>getGenericArgType(ConcreteChild.class, 1)).isEqualTo(Owner.class);
        }

        @Test
        @DisplayName("resolves through an intermediate subclass that adds no type arguments")
        void resolvesThroughSubclassHierarchy() {
            assertThat(ReflectionUtils.<Product>getGenericArgType(GrandChild.class, 0)).isEqualTo(Product.class);
        }
    }

    @Nested
    class PropertyValue {

        @Test
        void readsSimpleProperty() throws Exception {
            Product product = new Product();
            product.setCode("A-1");
            assertThat(ReflectionUtils.getPropertyValue(product, "code")).isEqualTo("A-1");
        }

        @Test
        @DisplayName("reads a nested path — the mechanism table headers use")
        void readsNestedPath() throws Exception {
            Company company = new Company();
            company.setTitle("Bardin");
            Owner owner = new Owner();
            owner.setCompany(company);
            Product product = new Product();
            product.setOwner(owner);

            assertThat(ReflectionUtils.getPropertyValue(product, "owner.company.title")).isEqualTo("Bardin");
        }

        @Test
        @DisplayName("a null in the middle of the path yields null instead of throwing")
        void returnsNullWhenPathIsInterrupted() throws Exception {
            assertThat(ReflectionUtils.getPropertyValue(new Product(), "owner.company.title")).isNull();
        }

        @Test
        void failsForUnknownProperty() {
            assertThatThrownBy(() -> ReflectionUtils.getPropertyValue(new Product(), "notExist"))
                    .isInstanceOf(Exception.class);
        }
    }

    @Nested
    class Instantiation {

        @Test
        void createsInstanceOfClassWithDefaultConstructor() {
            assertThat(ReflectionUtils.newInstance(Product.class)).isNotNull().isInstanceOf(Product.class);
        }
    }

    @Nested
    class Supers {

        @Test
        void listsSuperTypes() {
            List<Class<?>> supers = ReflectionUtils.getSupersOf(GrandChild.class, true, true, false);
            assertThat(supers).contains(ConcreteChild.class, GenericBase.class);
        }
    }
}
