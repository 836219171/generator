/*
 * Copyright (c) 2011-2020, baomidou (jobob@qq.com).
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * <p>
 * https://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.baomidou.mybatisplus.generator.config.builder;

import com.baomidou.mybatisplus.generator.config.StrategyConfig;

/**
 * 配置构建
 *
 * @author nieqiurong 2020/10/11.
 * @since 3.4.1
 */
public class BaseBuilder {

    private final StrategyConfig strategyConfig;

    public BaseBuilder(StrategyConfig strategyConfig) {
        this.strategyConfig = strategyConfig;
    }

    public Entity.Builder entityBuilder() {
        return strategyConfig.entityBuilder();
    }

    public Controller.Builder controllerBuilder() {
        return strategyConfig.controllerBuilder();
    }

    public Mapper.Builder mapperBuilder() {
        return strategyConfig.mapperBuilder();
    }

    public Service.Builder serviceBuilder() {
        return strategyConfig.serviceBuilder();
    }

    public StrategyConfig build() {
        return this.strategyConfig;
    }
}
