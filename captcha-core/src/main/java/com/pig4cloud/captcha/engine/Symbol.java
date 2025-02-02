/*
 * Copyright (c) 2019-2029, Dreamlu 卢春梦 (596392912@qq.com & www.dreamlu.net).
 * <p>
 * Licensed under the GNU LESSER GENERAL PUBLIC LICENSE 3.0;
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.gnu.org/licenses/lgpl.html
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.pig4cloud.captcha.engine;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 标识符
 *
 * @author L.cm
 */
@Getter
@RequiredArgsConstructor
public enum Symbol {

	/**
	 * 标识符
	 */
	NUM("n", false),

	/**
	 * 加法
	 */
	ADD("+", false),

	/**
	 * 减发
	 */
	SUB("-", false),

	/**
	 * 乘法
	 */
	MUL("x", true),

	/**
	 * 除法
	 */
	DIV("÷", true);

	/**
	 * 算数符号
	 */
	private final String value;

	/**
	 * 是否优先计算
	 */
	private final boolean priority;

	public static Symbol of(String c) {
		Symbol[] values = Symbol.values();
		for (Symbol value : values) {
			if (value.value.equals(c)) {
				return value;
			}
		}
		throw new IllegalArgumentException("不支持的标识符，仅仅支持(+、-、×、÷)");
	}

}
