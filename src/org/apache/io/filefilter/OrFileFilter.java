/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.io.filefilter;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * A {@link java.io.FileFilter} providing conditional OR logic across a list of
 * file filters. This filter returns {@code true} if any filters in the list
 * return {@code true}. Otherwise, it returns {@code false}. Checking of the
 * file filter list stops when the first filter returns {@code true}.
 *
 * @since 1.0
 * @version $Id: OrFileFilter.java 1307462 2012-03-30 15:13:11Z ggregory $
 * @see FileFilterUtils#or(IOFileFilter...)
 */
public class OrFileFilter
		extends AbstractFileFilter
		implements ConditionalFileFilter, Serializable {

	/**  */
	private static final long serialVersionUID = 1L;
	/** The list of file filters. */
	private final List<IOFileFilter> fileFilters;

	/**
	 * Constructs a new instance of <code>OrFileFilter</code>.
	 *
	 * @since 1.1
	 */
	public OrFileFilter() {
		fileFilters = new ArrayList<IOFileFilter>();
	}

	/**
	 * Constructs a new instance of <code>OrFileFilter</code> with the specified
	 * filters.
	 *
	 * @param fileFilters
	 *            the file filters for this filter, copied, null ignored
	 * @since 1.1
	 */
	public OrFileFilter(final List<IOFileFilter> fileFilters) {
		if (fileFilters == null) {
			this.fileFilters = new ArrayList<IOFileFilter>();
		}
		else {
			this.fileFilters = new ArrayList<IOFileFilter>(fileFilters);
		}
	}

	/**
	 * Constructs a new file filter that ORs the result of two other filters.
	 * 
	 * @param filter1
	 *            the first filter, must not be null
	 * @param filter2
	 *            the second filter, must not be null
	 * @throws IllegalArgumentException
	 *             if either filter is null
	 */
	public OrFileFilter(IOFileFilter filter1, IOFileFilter filter2) {
		if (filter1 == null || filter2 == null) {
			throw new IllegalArgumentException("The filters must not be null");
		}
		fileFilters = new ArrayList<IOFileFilter>(2);
		addFileFilter(filter1);
		addFileFilter(filter2);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void addFileFilter(final IOFileFilter ioFileFilter) {
		fileFilters.add(ioFileFilter);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<IOFileFilter> getFileFilters() {
		return Collections.unmodifiableList(fileFilters);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean removeFileFilter(IOFileFilter ioFileFilter) {
		return fileFilters.remove(ioFileFilter);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setFileFilters(final List<IOFileFilter> fileFilters) {
		this.fileFilters.clear();
		this.fileFilters.addAll(fileFilters);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean accept(final File file) {
		for (IOFileFilter fileFilter : fileFilters) {
			if (fileFilter.accept(file)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean accept(final File file, final String name) {
		for (IOFileFilter fileFilter : fileFilters) {
			if (fileFilter.accept(file, name)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Provide a String representaion of this file filter.
	 *
	 * @return a String representaion
	 */
	@Override
	public String toString() {
		StringBuilder buffer = new StringBuilder();
		buffer.append(super.toString());
		buffer.append("(");
		if (fileFilters != null) {
			for (int i = 0; i < fileFilters.size(); i++) {
				if (i > 0) {
					buffer.append(",");
				}
				Object filter = fileFilters.get(i);
				buffer.append(filter == null ? "null" : filter.toString());
			}
		}
		buffer.append(")");
		return buffer.toString();
	}

}
