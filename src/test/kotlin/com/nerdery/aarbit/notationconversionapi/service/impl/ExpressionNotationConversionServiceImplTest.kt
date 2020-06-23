package com.nerdery.aarbit.notationconversionapi.service.impl

import com.nerdery.aarbit.notationconversionapi.TestConfig
import com.nerdery.aarbit.notationconversionapi.enum.NotationType
import com.nerdery.aarbit.notationconversionapi.exception.InvalidExpressionException
import com.nerdery.aarbit.notationconversionapi.exception.InvalidOperandException
import com.nerdery.aarbit.notationconversionapi.service.NotationConversionService
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.StringSpec
import io.kotest.data.forAll
import io.kotest.data.row
import io.kotest.matchers.shouldBe
import org.springframework.test.context.ContextConfiguration

@ContextConfiguration(classes = [(TestConfig::class)])
class NotationConversionServiceImplTest(notationConversionService: NotationConversionService): StringSpec() {

    init {
        // Postfix-Postfix conversion tests
        "Valid postfix input should match output" {
            forAll(
                row(listOf("1","2","+")),
                row(listOf("1","2","3","-","+"))
            ) { expression ->
                notationConversionService.convert(expression, NotationType.POSTFIX, NotationType.POSTFIX) shouldBe expression
            }
        }
        "Postfix with invalid structure should throw InvalidExpressionException" {
            forAll(
                row(listOf("1")),
                row(listOf("1","+","2")),
                row(listOf("1","2","3","-")),
                row(listOf("1","2","="))
            ) { expression ->
                shouldThrow<InvalidExpressionException> {
                    notationConversionService.convert(expression, NotationType.POSTFIX, NotationType.POSTFIX)
                }
            }
        }
        "Postfix with invalid operands should throw InvalidOperandException" {
            forAll(
                row(listOf("1","b","+")),
                row(listOf("a","2","/"))
            ) {
                expression ->
                shouldThrow<InvalidOperandException> {
                    notationConversionService.convert(expression, NotationType.POSTFIX, NotationType.POSTFIX)
                }
            }
        }
        // Prefix-Postfix conversion tests
        "Valid prefix input should convert to postfix" {
            forAll(
                row(listOf("+","1","2"), listOf("1","2","+")),
                row(listOf("-","+","1","2","3"), listOf("1","2","+","3","-"))
            ) { expression, result ->
                notationConversionService.convert(expression, NotationType.PREFIX, NotationType.POSTFIX) shouldBe result
            }
        }
        "Prefix with invalid structure should throw InvalidExpressionException" {
            forAll(
                row(listOf("2")),
                row(listOf("1","+","2")),
                row(listOf("*","2","3","4")),
                row(listOf(")","2","4"))
            ) { expression ->
                shouldThrow<InvalidExpressionException> {
                    notationConversionService.convert(expression, NotationType.PREFIX, NotationType.POSTFIX)
                }
            }
        }
        "Prefix with invalid operands should throw InvalidOperandException" {
            forAll(
                row(listOf("+","1","b")),
                row(listOf("*","a","2"))
            ) {
                    expression ->
                shouldThrow<InvalidOperandException> {
                    notationConversionService.convert(expression, NotationType.PREFIX, NotationType.POSTFIX)
                }
            }
        }
        // Infix-Postfix conversion tests
        "Valid infix input should convert to postfix" {
            forAll(
                row(listOf("1","+","2"), listOf("1","2","+")),
                row(listOf("20","+","10","*","5"), listOf("20","10","5","*","+"))
            ) {
                expression, result ->
                notationConversionService.convert(expression, NotationType.INFIX, NotationType.POSTFIX) shouldBe result
            }
        }
        "Invalid infix input should throw InvalidExpressionException" {
            forAll(
                row(listOf("1","+","2","-"))
            ) {
                expression ->
                shouldThrow<InvalidExpressionException> {
                    notationConversionService.convert(expression, NotationType.INFIX, NotationType.POSTFIX)
                }
            }
        }
    }
}