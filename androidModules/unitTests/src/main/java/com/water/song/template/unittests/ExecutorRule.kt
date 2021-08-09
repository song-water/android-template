package com.water.song.template.unittests

import org.junit.rules.TestWatcher
import org.junit.runner.Description

/**
 *  @author HelloWorld
 *  ExecutorRule
 */
class ExecutorRule : TestWatcher() {

    override fun starting(description: Description?) {
        super.starting(description)
    }

    override fun finished(description: Description?) {
        super.finished(description)
    }
}