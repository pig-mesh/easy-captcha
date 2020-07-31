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

import java.util.Stack;

/**
 * 执行器
 *
 * @author L.cm
 */
public class Evaluator {

	protected String value;

	protected int start;

	protected final int end;

	public Evaluator(String source) {
		this.value = source.trim();
		if (value.startsWith("\uFEFF")) {
			value = value.substring(1);
		}
		this.end = this.value.length();
	}

	public static int eval(String expr) {
		return new Evaluator(expr).evaluate();
	}

	protected Stack<Token> parse() {
		Stack<Token> number = new Stack<>();
		Stack<Token> operator = new Stack<>();
		while (start < end) {
			char c = value.charAt(start);
			if (c >= '0' && c <= '9') {
				int num = findNumber();
				number.add(new Token(Symbol.NUM, num));
			}
			else if (c == '+' || c == '-' || c == '×' || c == '÷') {
				Symbol symbol = Symbol.of(c);
				Token token = new Token(symbol);
				if (operator.isEmpty()) {
					operator.push(token);
				}
				else if (symbol.isPriority() && !operator.peek().getSymbol().isPriority()) {
					operator.push(token);
				}
				else {
					number.push(operator.pop());
					operator.push(token);
				}
				start++;
			}
			skipSpace();
		}
		Stack<Token> result = new Stack<>();
		for (Token token : operator) {
			number.push(token);
		}
		for (Token token : number) {
			result.push(token);
		}
		return result;
	}

	/**
	 * 计算后序表达式的值
	 * @return 计算结果
	 */
	public int evaluate() {
		return evaluate(parse());
	}

	protected int evaluate(Stack<Token> stack) {
		Stack<Integer> evalStack = new Stack<>();
		for (Token token : stack) {
			Symbol symbol = token.getSymbol();
			if (Symbol.NUM == symbol) {
				evalStack.push(token.getValue());
			}
			else {
				// 遇到操作符时栈内至少应该有两个元素
				if (evalStack.size() < 2) {
					break;
				}
				// 弹出将要进行计算的数值
				int num1 = evalStack.pop();
				int num2 = evalStack.pop();
				switch (symbol) {
				case ADD:
					evalStack.push(num2 + num1);
					break;
				case SUB:
					evalStack.push(num2 - num1);
					break;
				case MUL:
					evalStack.push(num2 * num1);
					break;
				case DIV:
					evalStack.push(num2 / num1);
					break;
				default:
					break;
				}
			}
		}
		return evalStack.pop();
	}

	protected void skipSpace() {
		while (start < end) {
			if (!Character.isWhitespace(value.charAt(start))) {
				break;
			}
			start++;
		}
	}

	protected int findNumber() {
		char c = value.charAt(start++);
		int num = c - '0';
		while (start < end) {
			c = value.charAt(start++);
			if (c >= '0' && c <= '9') {
				num = num * 10 + (c - '0');
			}
			else {
				start--;
				break;
			}
		}
		return num;
	}

}
