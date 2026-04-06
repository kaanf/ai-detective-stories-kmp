package com.kaanf.home.domain.model

data class Jobs(
    val jobs: List<Job>
)

data class Job(
    val type: JobType,
    val nextRunAt: String,
    val timeUntilNextRun: String
) {
    fun timeUntilNextRunInSeconds(): Int {
        val parts = timeUntilNextRun.split(":")
        if (parts.size != 3) return 0
        val hours = parts[0].toIntOrNull() ?: return 0
        val minutes = parts[1].toIntOrNull() ?: return 0
        val seconds = parts[2].toIntOrNull() ?: return 0
        return hours * 3600 + minutes * 60 + seconds
    }
}

enum class JobType {
    Energy, Case
}
