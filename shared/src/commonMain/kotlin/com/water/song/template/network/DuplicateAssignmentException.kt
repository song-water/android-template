package com.water.song.template.network

class DuplicateAssignmentException(
    message: String
) : RuntimeException("$message, should be assignment only once.")