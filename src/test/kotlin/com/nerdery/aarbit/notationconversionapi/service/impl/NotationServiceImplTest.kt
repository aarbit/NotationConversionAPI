package com.nerdery.aarbit.notationconversionapi.service.impl

import com.nerdery.aarbit.notationconversionapi.TestConfig
import com.nerdery.aarbit.notationconversionapi.enum.NotationType
import com.nerdery.aarbit.notationconversionapi.exception.InvalidNotationException
import com.nerdery.aarbit.notationconversionapi.service.NotationService
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.StringSpec
import io.kotest.data.forAll
import io.kotest.data.row
import io.kotest.matchers.shouldBe
import org.springframework.test.context.ContextConfiguration

@ContextConfiguration(classes = [(TestConfig::class)])
class NotationServiceImplTest(notationService: NotationService) : StringSpec() {

    init {
        "Infix expression should be NotationType.INFIX" {
            notationService.determineNotationType("1 + 2".split(" ")) shouldBe NotationType.INFIX
        }
        "Postfix expression should be NotationType.POSTFIX" {
            notationService.determineNotationType("1 2 +".split(" ")) shouldBe NotationType.POSTFIX
        }
        "Prefix expression should be NotationType.PREFIX" {
            notationService.determineNotationType("+ 1 2".split(" ")) shouldBe NotationType.PREFIX
        }
        "Valid notation types should match their Enum" {
            forAll(
                row("Prefix", NotationType.PREFIX),
                row("postfix", NotationType.POSTFIX),
                row("INFIX", NotationType.INFIX)
            ) { input, output ->
                notationService.parseNotationType(input) shouldBe output
            }
        }
        "Invalid notation types should throw InvalidNotationException" {
            shouldThrow<InvalidNotationException> { notationService.parseNotationType("roundfix")  }
        }
    }
}