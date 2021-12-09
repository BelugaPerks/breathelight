package com.belugaperks.breathelight_sleepassist.tests;

import cucumber.api.CucumberOptions;
import cucumber.api.android.CucumberAndroidJUnitRunner;


@CucumberOptions(
        features = { "features" },
        plugin = { "pretty" },
        glue = { "com.belugaperks.breathelight_sleepassist.tests" },
        tags = { "~@wip" }
)
public class BreatheLightTestRunner extends CucumberAndroidJUnitRunner{
}
