package com.nerdery.aarbit.notationconversionapi.controller

import com.nerdery.aarbit.notationconversionapi.helper.joinWithSpaces
import com.nerdery.aarbit.notationconversionapi.helper.splitToNoBlanksList
import com.nerdery.aarbit.notationconversionapi.service.NotationConversionService
import com.nerdery.aarbit.notationconversionapi.service.NotationService
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.cache.annotation.Cacheable
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class ConversionController @Autowired constructor (private val notationService: NotationService,
                                                   private val notationConversionService: NotationConversionService) {
    @PostMapping(path = ["/convert/output/{notationType}"])
    @Cacheable("expression")
    fun convertExpression(@RequestBody expression: String, @PathVariable notationType: String): ResponseEntity<String> {
        return try {
            val expressionAsList = expression.splitToNoBlanksList()
            val inputNotationType = notationService.determineNotationType(expressionAsList)
            val outputNotationType = notationService.parseNotationType(notationType)
            val convertedExpression = notationConversionService.convert(expressionAsList, inputNotationType, outputNotationType)
            ResponseEntity(convertedExpression.joinWithSpaces(), HttpStatus.OK)
        } catch (e: Exception) {
            log.error("Conversion failure", e)
            ResponseEntity(e.message, HttpStatus.BAD_REQUEST)
        }
    }

    companion object {
        val log: Logger = LoggerFactory.getLogger(ConversionController::class.java)
    }

}