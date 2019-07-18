package me.gangplank.forecastmvvm.data.provider

import me.gangplank.forecastmvvm.internal.UnitSystem

interface UnitProvider {
    fun getUnitSystem(): UnitSystem
}