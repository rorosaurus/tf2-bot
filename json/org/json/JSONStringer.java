/******************************************************************************
 * This file is part of tf2-bot.                                              *
 *                                                                            *
 * tf2-bot is free software: you can redistribute it and/or modify            *
 * it under the terms of the GNU General Public License as published by       *
 * the Free Software Foundation, either version 3 of the License, or          *
 * (at your option) any later version.                                        *
 *                                                                            *
 * tf2-bot is distributed in the hope that it will be useful,                 *
 * but WITHOUT ANY WARRANTY; without even the implied warranty of             *
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the              *
 * GNU General Public License for more details.                               *
 *                                                                            *
 * You should have received a copy of the GNU General Public License          *
 * along with tf2-bot.  If not, see <http://www.gnu.org/licenses/>.           *
 ******************************************************************************/

package org.json;

import java.io.StringWriter;

public class JSONStringer extends JSONWriter {
    /**
     * Make a fresh JSONStringer. It can be used to build one JSON text.
     */
    public JSONStringer() {
        super(new StringWriter());
    }

    /**
     * Return the JSON text. This method is used to obtain the product of the
     * JSONStringer instance. It will return <code>null</code> if there was a
     * problem in the construction of the JSON text (such as the calls to
     * <code>array</code> were not properly balanced with calls to
     * <code>endArray</code>).
     * @return The JSON text.
     */
    public String toString() {
        return this.mode == 'd' ? this.writer.toString() : null;
    }
}
