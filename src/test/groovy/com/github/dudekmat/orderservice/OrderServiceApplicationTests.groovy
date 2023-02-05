package com.github.dudekmat.orderservice

import org.springframework.boot.test.context.SpringBootTest
import spock.lang.Specification

@SpringBootTest
class OrderServiceApplicationTests extends Specification {

    def 'context loads'() {
        expect:
        1 == 1
    }
}
