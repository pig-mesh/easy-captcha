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

/**
 * Token
 *
 * @author L.cm
 */
public class Token {

	private final Symbol symbol;

	private Integer value;

	public Token(Symbol symbol) {
		this(symbol, null);
	}

	public Token(Symbol symbol, Integer value) {
		this.symbol = symbol;
		this.value = value;
	}

	public Symbol getSymbol() {
		return this.symbol;
	}

	public Integer getValue() {
		return value;
	}

	@Override
	public String toString() {
		return value == null ? symbol.getValue() + "" : value + "";
	}

}
