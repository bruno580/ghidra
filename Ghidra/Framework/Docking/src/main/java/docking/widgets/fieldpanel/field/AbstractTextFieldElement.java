/* ###
 * IP: GHIDRA
 * REVIEWED: YES
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package docking.widgets.fieldpanel.field;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JComponent;

import docking.widgets.fieldpanel.support.RowColLocation;

/**
 * An object that wraps a string and provides data that describes how to render
 * that string.  
 * <p>
 * This class was created as a place to house attributes of rendering that 
 * are not described by Java's Font object, like underlining.
 * 
 * 
 */

abstract public class AbstractTextFieldElement implements FieldElement {

	/** the attributed string displayed by this field element */
	protected AttributedString attributedString;
	/** the row within the field where this element begins */
	protected int row;
	/** the offset within the field's row where this element begins */
	protected int column;

	protected AbstractTextFieldElement(AttributedString attributedString, int row, int column) {
		this.attributedString = attributedString;
		this.row = row;
		this.column = column;
	}

	/**
	 * @see docking.widgets.fieldpanel.field.FieldElement#getText()
	 */
	@Override
	public String getText() {
		return attributedString.getText();
	}

	/**
	 * @see docking.widgets.fieldpanel.field.FieldElement#charAt(int)
	 */
	@Override
	public char charAt(int index) {
		return attributedString.getText().charAt(index);
	}

	/**
	 * @see docking.widgets.fieldpanel.field.FieldElement#length()
	 */
	@Override
	public int length() {
		return getText().length();
	}

//==================================================================================================
// font metrics methods
//==================================================================================================

	/**
	 * @see docking.widgets.fieldpanel.field.FieldElement#getStringWidth()
	 */
	@Override
	public int getStringWidth() {
		return attributedString.getStringWidth();
	}

	/**
	 * @see docking.widgets.fieldpanel.field.FieldElement#getHeightAbove()
	 */
	@Override
	public int getHeightAbove() {
		return attributedString.getHeightAbove();
	}

	/**
	 * @see docking.widgets.fieldpanel.field.FieldElement#getHeightBelow()
	 */
	@Override
	public int getHeightBelow() {
		return attributedString.getHeightBelow();
	}

	/**
	 * @see docking.widgets.fieldpanel.field.FieldElement#getMaxCharactersForWidth(int)
	 */
	@Override
	public int getMaxCharactersForWidth(int width) {
		return attributedString.getColumnPosition(width);
	}

	/**
	 * @see docking.widgets.fieldpanel.field.FieldElement#getColor(int)
	 */
	@Override
	public Color getColor(int charIndex) {
		return attributedString.getColor(charIndex);
	}

	/**
	 * @see docking.widgets.fieldpanel.field.FieldElement#getFieldElement(int)
	 */
	@Override
	public FieldElement getFieldElement(int characterOffset) {
		return this;
	}

	/**
	 * @see docking.widgets.fieldpanel.field.FieldElement#substring(int)
	 */
	@Override
	public FieldElement substring(int start) {
		return substring(start, attributedString.length());
	}

	@Override
	public String toString() {
		return attributedString.getText();
	}

//==================================================================================================
// location info
//==================================================================================================
	/**
	 * @see docking.widgets.fieldpanel.field.FieldElement#getDataLocationForCharacterIndex(int)
	 */
	@Override
	public RowColLocation getDataLocationForCharacterIndex(int characterIndex) {
		if (characterIndex < 0 || characterIndex > attributedString.getText().length()) {
			throw new IllegalArgumentException("columnPosition is out of range: " + characterIndex);
		}
		return new RowColLocation(row, column + characterIndex);
	}

	/**
	 * @see docking.widgets.fieldpanel.field.FieldElement#getCharacterIndexForDataLocation(int, int)
	 */
	@Override
	public int getCharacterIndexForDataLocation(int dataRow, int dataColumn) {
		if (dataRow == row && (dataColumn >= column) && (dataColumn <= column + length())) {
			return dataColumn - column;
		}

		return -1;
	}

//==================================================================================================
// paint methods
//==================================================================================================

	/**
	 * @see docking.widgets.fieldpanel.field.FieldElement#paint(java.awt.Graphics, int, int)
	 */
	@Override
	public void paint(JComponent c, Graphics g, int x, int y) {
		attributedString.paint(c, g, x, y);
	}
}
