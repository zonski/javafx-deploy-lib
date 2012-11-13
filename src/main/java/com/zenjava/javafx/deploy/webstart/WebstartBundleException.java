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
package com.zenjava.javafx.deploy.webstart;

import com.zenjava.javafx.deploy.JavaFxBundleException;

public class WebstartBundleException extends JavaFxBundleException {

    public WebstartBundleException(String message) {
        super(message);
    }

    public WebstartBundleException(String message, Throwable cause) {
        super(message, cause);
    }
}
