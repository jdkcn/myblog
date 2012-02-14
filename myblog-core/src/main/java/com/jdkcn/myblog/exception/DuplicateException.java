/**
 * Copyright (c) 2005-2011, Rory Ye
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 *     * Redistributions of source code must retain the above copyright notice,
 *       this list of conditions and the following disclaimer.
 *     * Redistributions in binary form must reproduce the above copyright notice,
 *       this list of conditions and the following disclaimer in the documentation
 *       and/or other materials provided with the distribution.
 *     * Neither the name of the Jdkcn.com nor the names of its contributors may
 *       be used to endorse or promote products derived from this software without
 *       specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR
 * ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
 * ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
/**
 * 
 */
package com.jdkcn.myblog.exception;

import org.apache.commons.lang.exception.NestableRuntimeException;

/**
 * @author <a href="mailto:rory.cn@gmail.com">Rory</a>
 * @version $Id: DuplicateException.java 404 2011-04-28 11:58:50Z rory.cn $
 */
public class DuplicateException extends NestableRuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7923812903904409142L;

	public DuplicateException(String type, String name, Object value) {
		super("The [" + type + "] has duplicate with [" + name + ":" + value + "]");
	}
	
	/**
	 * 
	 */
	public DuplicateException() {
		super();
	}

	/**
	 * @param msg
	 * @param cause
	 */
	public DuplicateException(String msg, Throwable cause) {
		super(msg, cause);
	}

	/**
	 * @param msg
	 */
	public DuplicateException(String msg) {
		super(msg);
	}

	/**
	 * @param cause
	 */
	public DuplicateException(Throwable cause) {
		super(cause);
	}

}
