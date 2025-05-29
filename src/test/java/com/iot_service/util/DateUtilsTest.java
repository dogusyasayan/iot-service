package com.iot_service.util;

import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

import static org.assertj.core.api.Assertions.assertThat;

class DateUtilsTest {

    @Test
    void it_should_convert_epoch_to_date_string() {
        // given
        long epochMillis = 1716806400000L;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
                .withZone(ZoneId.systemDefault());
        String expected = formatter.format(Instant.ofEpochMilli(epochMillis));

        // when
        String result = DateUtils.epochMillisToString(epochMillis);

        // then
        assertThat(result).isEqualTo(expected);
    }
}
