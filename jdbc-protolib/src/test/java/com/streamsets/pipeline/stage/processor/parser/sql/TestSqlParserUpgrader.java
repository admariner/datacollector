/*
 * Copyright 2021 StreamSets Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.streamsets.pipeline.stage.processor.parser.sql;

import com.streamsets.pipeline.api.Config;
import com.streamsets.pipeline.api.StageUpgrader;
import com.streamsets.pipeline.config.upgrade.UpgraderTestUtils;
import com.streamsets.pipeline.lib.jdbc.connection.upgrader.JdbcConnectionUpgradeTestUtil;
import com.streamsets.pipeline.lib.jdbc.parser.sql.UnsupportedFieldTypeValues;
import com.streamsets.pipeline.stage.origin.jdbc.cdc.oracle.OracleCDCSourceUpgrader;
import com.streamsets.pipeline.upgrader.SelectorStageUpgrader;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.net.URL;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TestSqlParserUpgrader {

  private StageUpgrader upgrader;

  @Before
  public void setUp() {
    URL yamlResource = ClassLoader.getSystemClassLoader().getResource("upgrader/SqlParserDProcessor.yaml");
    upgrader = new SelectorStageUpgrader("stage", null, yamlResource);
  }

  @Test
  public void upgradeV1TOV2() {
    StageUpgrader.Context context = Mockito.mock(StageUpgrader.Context.class);
    Mockito.doReturn(1).when(context).getFromVersion();
    Mockito.doReturn(2).when(context).getToVersion();

    List<Config> configs = new ArrayList<>();
    configs = upgrader.upgrade(configs, context);

    UpgraderTestUtils.assertExists(configs,"configBean.putPseudocolumnsInHeader", false);
  }
}
