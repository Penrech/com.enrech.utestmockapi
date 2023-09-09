package com.enrech.common

interface BaseMapper <OUT> {
    fun mapTo(): OUT
}

fun<OUT> Iterable<BaseMapper<OUT>>.mapTo() = this.map { it.mapTo() }