/*
 * Copyright (C) 2013 Burton Alexander
 * 
 * This program is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later
 * version.
 * 
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU General Public License along with
 * this program; if not, write to the Free Software Foundation, Inc., 51
 * Franklin Street, Fifth Floor, Boston, MA 02110-1301, USA.
 * 
 */
package asia.stampy.common.message;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

/**
 * Abstract implementation of a {@link StampyMessageHeader}.
 */
public abstract class AbstractMessageHeader implements StampyMessageHeader {

	private static final long serialVersionUID = 4570408820942642173L;

	/** The Constant CONTENT_LENGTH. */
	public static final String CONTENT_LENGTH = "content-length";

	private Map<String, String> headers = new HashMap<>();

	/**
	 * Sets the content length.
	 * 
	 * @param length
	 *          the new content length
	 */
	public void setContentLength(int length) {
		addHeader(CONTENT_LENGTH, Integer.toString(length));
	}

	/**
	 * Gets the content length.
	 * 
	 * @return the content length
	 */
	public int getContentLength() {
		String length = getHeaderValue(CONTENT_LENGTH);
		if (length == null) return -1;

		return Integer.parseInt(length);
	}

	/**
	 * <i>If a client or a server receives repeated frame header entries, only the
	 * first header entry SHOULD be used as the value of header entry. Subsequent
	 * values are only used to maintain a history of state changes of the header
	 * and MAY be ignored.</i><br>
	 * <br>
	 * If addHeader is invoked when the key specified is already in use the value
	 * is NOT updated. To replace an existing key-value, first use the
	 * {@link AbstractMessageHeader#removeHeader(String)} method.
	 */
	public void addHeader(String key, String value) {
		if (!hasHeader(key)) headers.put(key, value);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * asia.stampy.common.message.StampyMessageHeader#removeHeader(java.lang.String
	 * )
	 */
	public void removeHeader(String key) {
		headers.remove(key);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * asia.stampy.common.message.StampyMessageHeader#getHeaderValue(java.lang
	 * .String)
	 */
	public String getHeaderValue(String key) {
		return headers.get(key);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * asia.stampy.common.message.StampyMessageHeader#hasHeader(java.lang.String)
	 */
	public boolean hasHeader(String key) {
		return headers.containsKey(key);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see asia.stampy.common.message.StampyMessageHeader#toMessageHeader()
	 */
	@Override
	public final String toMessageHeader() {
		boolean first = true;

		StringBuilder builder = new StringBuilder();
		for (Entry<String, String> entry : headers.entrySet()) {
			if (!first) builder.append("\n");

			builder.append(entry.getKey());
			builder.append(":");
			builder.append(entry.getValue());

			first = false;
		}

		return builder.toString();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see asia.stampy.common.message.StampyMessageHeader#getHeaders()
	 */
	public Map<String, String> getHeaders() {
		return new HashMap<>(headers);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	public boolean equals(Object o) {
		return EqualsBuilder.reflectionEquals(this, o);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return toMessageHeader();
	}

}
