package com.vinylsmobile.model

import androidx.room.TypeConverter

class PerformerTypeConverter {
    @TypeConverter
    fun fromPerformerType(type: PerformerType): String {
        return type.name
    }

    @TypeConverter
    fun toPerformerType(type: String): PerformerType {
        return PerformerType.valueOf(type)
    }
}
