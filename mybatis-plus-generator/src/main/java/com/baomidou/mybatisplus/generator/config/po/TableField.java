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
package com.baomidou.mybatisplus.generator.config.po;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.rules.IColumnType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Map;

/**
 * 表字段信息
 *
 * @author YangHu
 * @since 2016-12-03
 */
@Data
@Accessors(chain = true)
public class TableField {
    private boolean convert;
    private boolean keyFlag;
    /**
     * 主键是否为自增类型
     */
    private boolean keyIdentityFlag;
    private String name;
    private String type;
    private String propertyName;
    private IColumnType columnType;
    private String comment;
    private String fill;
    /**
     * 是否关键字
     *
     * @since 3.3.2
     */
    private boolean keyWords;
    /**
     * 数据库字段（关键字含转义符号）
     *
     * @since 3.3.2
     */
    private String columnName;
    /**
     * 自定义查询字段列表
     */
    private Map<String, Object> customMap;

    /**
     * @param convert
     * @return this
     * @see #setConvert(StrategyConfig)
     * @deprecated 3.4.1
     */
    public TableField setConvert(boolean convert) {
        this.convert = convert;
        return this;
    }

    protected TableField setConvert(StrategyConfig strategyConfig) {
        if (strategyConfig.entity().isTableFieldAnnotationEnable() || isKeyWords()) {
            this.convert = true;
            return this;
        }
        if (strategyConfig.isCapitalModeNaming(name)) {
            this.convert = !name.equalsIgnoreCase(propertyName);
        } else {
            // 转换字段
            if (NamingStrategy.underline_to_camel == strategyConfig.entity().getColumnNaming()) {
                // 包含大写处理
                if (StringUtils.containsUpperCase(name)) {
                    this.convert = true;
                }
            } else if (!name.equals(propertyName)) {
                this.convert = true;
            }
        }
        return this;
    }


    /**
     * @param propertyName 属性名称
     * @return this
     * @deprecated 3.4.1 {@link #setPropertyName(String, StrategyConfig, IColumnType)}
     */
    @Deprecated
    public TableField setPropertyName(String propertyName) {
        this.propertyName = propertyName;
        return this;
    }

    /**
     * 设置属性名称
     *
     * @param propertyName   属性名
     * @param strategyConfig 策略配置
     * @param columnType     字段类型
     * @return this
     * @since 3.4.1
     */
    public TableField setPropertyName(String propertyName, StrategyConfig strategyConfig, IColumnType columnType) {
        this.columnType = columnType;
        if (strategyConfig.entity().isBooleanColumnRemoveIsPrefix()
            && "boolean".equalsIgnoreCase(this.getPropertyType()) && propertyName.startsWith("is")) {
            this.convert = true;
            this.propertyName = StringUtils.removePrefixAfterPrefixToLower(propertyName, 2);
            return this;
        }
        this.propertyName = propertyName;
        this.setConvert(strategyConfig);
        return this;
    }

    /**
     * 设置属性名称
     *
     * @param strategyConfig 策略配置
     * @param propertyName   属性名
     * @return this
     * @deprecated 3.4.1
     */
    @Deprecated
    public TableField setPropertyName(StrategyConfig strategyConfig, String propertyName) {
        return setPropertyName(propertyName, strategyConfig, this.columnType);
    }

    /**
     * @param columnType 字段类型
     * @return this
     * @deprecated 3.4.1 {@link #setPropertyName(String, StrategyConfig, IColumnType)}
     */
    @Deprecated
    public TableField setColumnType(IColumnType columnType) {
        this.columnType = columnType;
        return this;
    }

    public String getPropertyType() {
        if (null != columnType) {
            return columnType.getType();
        }
        return null;
    }

    /**
     * 按 JavaBean 规则来生成 get 和 set 方法后面的属性名称
     * 需要处理一下特殊情况：
     * <p>
     * 1、如果只有一位，转换为大写形式
     * 2、如果多于 1 位，只有在第二位是小写的情况下，才会把第一位转为小写
     * <p>
     * 我们并不建议在数据库对应的对象中使用基本类型，因此这里不会考虑基本类型的情况
     */
    public String getCapitalName() {
        if (propertyName.length() == 1) {
            return propertyName.toUpperCase();
        }
        if (Character.isLowerCase(propertyName.charAt(1))) {
            return Character.toUpperCase(propertyName.charAt(0)) + propertyName.substring(1);
        }
        return propertyName;
    }

    /**
     * 获取注解字段名称
     *
     * @return 字段
     * @since 3.3.2
     */
    public String getAnnotationColumnName() {
        if (keyWords) {
            if (columnName.startsWith("\"")) {
                return String.format("\\\"%s\\\"", name);
            }
        }
        return columnName;
    }

    /**
     * 是否为乐观锁字段
     *
     * @param versionFieldName 乐观锁属性字段
     * @return 是否为乐观锁字段
     * @since 3.4.1
     */
    public boolean isVersionField(String versionFieldName) {
        return StringUtils.isNotBlank(versionFieldName) && versionFieldName.equals(this.propertyName);
    }

    /**
     * 是否为逻辑删除字段
     *
     * @param logicDeleteFiledName 逻辑删除字段
     * @return 是否为逻辑删除字段
     * @since 3.4.1
     */
    public boolean isLogicDeleteFiled(String logicDeleteFiledName) {
        return StringUtils.isNotBlank(logicDeleteFiledName) && logicDeleteFiledName.equals(this.propertyName);
    }
}
