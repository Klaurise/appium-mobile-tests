package data;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CheckoutData {

    private String firstName;
    private String lastName;
    private String zipCode;

    public static final CheckoutData STANDARD_ADDRESS = CheckoutData.builder()
            .firstName("Jane")
            .lastName("Doe")
            .zipCode("02-310 Alabama")
            .build();
}
