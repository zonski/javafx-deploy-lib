/*
 * The JavaFX Deploy Lib provides a core functionality for assembling JavaFX applications
 * Copyright (C) 2012  Daniel Zwolenski
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.zenjava.javafx.deploy.log;

public abstract class AbstractLog implements Log {

    private LogLevel logLevel;

    public AbstractLog(LogLevel logLevel) {
        this.logLevel = logLevel;
    }

    public LogLevel getLogLevel() {
        return logLevel;
    }

    public void setLogLevel(LogLevel logLevel) {
        this.logLevel = logLevel;
    }

    @Override
    public void debug(String message, Object... values) {
        log(LogLevel.debug, message, values);
    }

    @Override
    public void info(String message, Object... values) {
        log(LogLevel.info, message, values);
    }

    @Override
    public void warn(String message, Object... values) {
        log(LogLevel.warn, message, values);
    }

    @Override
    public void error(String message, Object... values) {
        log(LogLevel.error, message, values);
    }

    @Override
    public void exception(String message, Throwable error, Object... values) {
        log(LogLevel.error, message, values);
    }

    protected boolean isLogLevelSet(LogLevel level) {
        return this.logLevel.ordinal() >= level.ordinal();
    }

    protected void log(LogLevel level, String message, Object... values) {
        if (isLogLevelSet(level)) {
            write(String.format(message, values));
        }
    }

    protected abstract void write(String message);
}
