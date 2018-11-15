/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.apache.fineract.cn.postgresql.util;

public final class JdbcUrlBuilder {

  private final DatabaseType type;
  private String host;
  private String port;
  private String instanceName;

  private JdbcUrlBuilder(final DatabaseType type) {
    super();
    this.type = type;
  }

  public static JdbcUrlBuilder create(final DatabaseType type) {
    return new JdbcUrlBuilder(type);
  }

  public JdbcUrlBuilder host(final String host) {
    this.host = host;
    return this;
  }

  public JdbcUrlBuilder port(final String port) {
    this.port = port;
    return this;
  }

  public JdbcUrlBuilder instanceName(final String instanceName) {
    this.instanceName = instanceName;
    return this;
  }

  public String build() {
    switch (this.type) {
      case POSTGRESQL:
        final StringBuilder jdbcUrl = new StringBuilder(this.type.getSubProtocol());
        if (this.host == null){
          if (this.instanceName == null){
            jdbcUrl.append("/");
          }
          else
            jdbcUrl.append(instanceName);
        }
        else {
          if (this.port == null){
            if (this.instanceName == null){
              jdbcUrl.append("//").append(this.host).append("/");
            }
            else
              jdbcUrl.append("//").append(this.host).append("/").append(this.instanceName);
          }
          else {
            if (this.instanceName == null){
              jdbcUrl.append("//").append(this.host).append(":").append(this.port).append("/");
            }
            else {
              jdbcUrl.append("//").append(this.host).append(":").append(this.port).append("/").append(instanceName);
            }
          }
        }
        return jdbcUrl.toString();
      default:
        throw new IllegalArgumentException("Unknown database type '" + this.type.name() + "'");
    }
  }

  public enum DatabaseType {
    POSTGRESQL("jdbc:postgresql:");

    private final String subProtocol;

    DatabaseType(final String subProtocol) {
      this.subProtocol = subProtocol;
    }

    String getSubProtocol() {
      return this.subProtocol;
    }
  }
}
