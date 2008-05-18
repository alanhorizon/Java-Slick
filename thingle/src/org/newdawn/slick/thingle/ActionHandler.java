package org.newdawn.slick.thingle;

import java.io.IOException;
import java.io.InputStream;
import java.util.ResourceBundle;

import org.newdawn.slick.Color;
import org.newdawn.slick.Font;
import org.newdawn.slick.Image;
import org.newdawn.slick.thingle.internal.Dimension;
import org.newdawn.slick.thingle.internal.Thinlet;

public class ActionHandler {
	private Thinlet thinlet;
	
	public ActionHandler() {
	}
	
	public void setThinlet(Thinlet thinlet) {
		this.thinlet = thinlet;
	}

	/**
	 * @param component
	 * @param key
	 * @param value
	 * @see org.newdawn.slick.thingle.internal.Thinlet#setBoolean(java.lang.Object, java.lang.String, boolean)
	 */
	protected void setBoolean(Object component, String key, boolean value) {
		thinlet.setBoolean(component, key, value);
	}

	/**
	 * @param component
	 * @param key
	 * @param value
	 * @see org.newdawn.slick.thingle.internal.Thinlet#setChoice(java.lang.Object, java.lang.String, java.lang.String)
	 */
	protected void setChoice(Object component, String key, String value) {
		thinlet.setChoice(component, key, value);
	}

	/**
	 * @param component
	 * @param key
	 * @param color
	 * @see org.newdawn.slick.thingle.internal.Thinlet#setColor(java.lang.Object, java.lang.String, org.newdawn.slick.Color)
	 */
	protected void setColor(Object component, String key, Color color) {
		thinlet.setColor(component, key, color);
	}

	/**
	 * @param background
	 * @param text
	 * @param textbackground
	 * @param border
	 * @param disable
	 * @param hover
	 * @param press
	 * @param focus
	 * @param select
	 * @see org.newdawn.slick.thingle.internal.Thinlet#setColors(int, int, int, int, int, int, int, int, int)
	 */
	protected void setColors(int background, int text, int textbackground, int border, int disable, int hover, int press, int focus, int select) {
		thinlet.setColors(background, text, textbackground, border, disable, hover, press, focus, select);
	}

	/**
	 * @param component
	 * @param font
	 * @see org.newdawn.slick.thingle.internal.Thinlet#setFont(java.lang.Object, org.newdawn.slick.Font)
	 */
	protected void setFont(Object component, Font font) {
		thinlet.setFont(component, font);
	}

	/**
	 * @param component
	 * @param key
	 * @param font
	 * @see org.newdawn.slick.thingle.internal.Thinlet#setFont(java.lang.Object, java.lang.String, org.newdawn.slick.Font)
	 */
	protected void setFont(Object component, String key, Font font) {
		thinlet.setFont(component, key, font);
	}

	/**
	 * @param component
	 * @param key
	 * @param icon
	 * @see org.newdawn.slick.thingle.internal.Thinlet#setIcon(java.lang.Object, java.lang.String, org.newdawn.slick.Image)
	 */
	protected void setIcon(Object component, String key, Image icon) {
		thinlet.setIcon(component, key, icon);
	}

	/**
	 * @param component
	 * @param key
	 * @param value
	 * @see org.newdawn.slick.thingle.internal.Thinlet#setInteger(java.lang.Object, java.lang.String, int)
	 */
	protected void setInteger(Object component, String key, int value) {
		thinlet.setInteger(component, key, value);
	}

	/**
	 * @param component
	 * @param key
	 * @param value
	 * @see org.newdawn.slick.thingle.internal.Thinlet#setKeystroke(java.lang.Object, java.lang.String, java.lang.String)
	 */
	protected void setKeystroke(Object component, String key, String value) {
		thinlet.setKeystroke(component, key, value);
	}

	/**
	 * @param component
	 * @param key
	 * @param value
	 * @param root
	 * @param handler
	 * @see org.newdawn.slick.thingle.internal.Thinlet#setMethod(java.lang.Object, java.lang.String, java.lang.String, java.lang.Object, java.lang.Object)
	 */
	protected void setMethod(Object component, String key, String value, Object root, Object handler) {
		thinlet.setMethod(component, key, value, root, handler);
	}

	/**
	 * @param resourcebundle
	 * @see org.newdawn.slick.thingle.internal.Thinlet#setResourceBundle(java.util.ResourceBundle)
	 */
	protected void setResourceBundle(ResourceBundle resourcebundle) {
		thinlet.setResourceBundle(resourcebundle);
	}

	/**
	 * @param component
	 * @param key
	 * @param value
	 * @see org.newdawn.slick.thingle.internal.Thinlet#setString(java.lang.Object, java.lang.String, java.lang.String)
	 */
	protected void setString(Object component, String key, String value) {
		thinlet.setString(component, key, value);
	}

	/**
	 * @param component
	 * @param key
	 * @return
	 * @see org.newdawn.slick.thingle.internal.Thinlet#getBoolean(java.lang.Object, java.lang.String)
	 */
	protected boolean getBoolean(Object component, String key) {
		return thinlet.getBoolean(component, key);
	}

	/**
	 * @param component
	 * @param key
	 * @return
	 * @see org.newdawn.slick.thingle.internal.Thinlet#getChoice(java.lang.Object, java.lang.String)
	 */
	protected String getChoice(Object component, String key) {
		return thinlet.getChoice(component, key);
	}

	/**
	 * @param component
	 * @param key
	 * @return
	 * @see org.newdawn.slick.thingle.internal.Thinlet#getColor(java.lang.Object, java.lang.String)
	 */
	protected Color getColor(Object component, String key) {
		return thinlet.getColor(component, key);
	}

	/**
	 * @param component
	 * @return
	 * @see org.newdawn.slick.thingle.internal.Thinlet#getCount(java.lang.Object)
	 */
	protected int getCount(Object component) {
		return thinlet.getCount(component);
	}

	/**
	 * @return
	 * @see org.newdawn.slick.thingle.internal.Thinlet#getDesktop()
	 */
	protected Object getDesktop() {
		return thinlet.getDesktop();
	}

	/**
	 * @param component
	 * @param key
	 * @return
	 * @see org.newdawn.slick.thingle.internal.Thinlet#getIcon(java.lang.Object, java.lang.String)
	 */
	protected Image getIcon(Object component, String key) {
		return thinlet.getIcon(component, key);
	}

	/**
	 * @param path
	 * @param preload
	 * @return
	 * @see org.newdawn.slick.thingle.internal.Thinlet#getIcon(java.lang.String, boolean)
	 */
	protected Image getIcon(String path, boolean preload) {
		return thinlet.getIcon(path, preload);
	}

	/**
	 * @param path
	 * @return
	 * @see org.newdawn.slick.thingle.internal.Thinlet#getIcon(java.lang.String)
	 */
	protected Image getIcon(String path) {
		return thinlet.getIcon(path);
	}

	/**
	 * @param component
	 * @param key
	 * @return
	 * @see org.newdawn.slick.thingle.internal.Thinlet#getInteger(java.lang.Object, java.lang.String)
	 */
	protected int getInteger(Object component, String key) {
		return thinlet.getInteger(component, key);
	}

	/**
	 * @param component
	 * @param index
	 * @return
	 * @see org.newdawn.slick.thingle.internal.Thinlet#getItem(java.lang.Object, int)
	 */
	protected Object getItem(Object component, int index) {
		return thinlet.getItem(component, index);
	}

	/**
	 * @param component
	 * @return
	 * @see org.newdawn.slick.thingle.internal.Thinlet#getItems(java.lang.Object)
	 */
	protected Object[] getItems(Object component) {
		return thinlet.getItems(component);
	}

	/**
	 * @param component
	 * @return
	 * @see org.newdawn.slick.thingle.internal.Thinlet#getParent(java.lang.Object)
	 */
	protected Object getParent(Object component) {
		return thinlet.getParent(component);
	}

	/**
	 * @return
	 * @see org.newdawn.slick.thingle.internal.Thinlet#getPreferredSize()
	 */
	protected Dimension getPreferredSize() {
		return thinlet.getPreferredSize();
	}

	/**
	 * @param component
	 * @param key
	 * @return
	 * @see org.newdawn.slick.thingle.internal.Thinlet#getProperty(java.lang.Object, java.lang.Object)
	 */
	protected Object getProperty(Object component, Object key) {
		return thinlet.getProperty(component, key);
	}

	/**
	 * @param component
	 * @return
	 * @see org.newdawn.slick.thingle.internal.Thinlet#getSelectedIndex(java.lang.Object)
	 */
	protected int getSelectedIndex(Object component) {
		return thinlet.getSelectedIndex(component);
	}

	/**
	 * @param component
	 * @return
	 * @see org.newdawn.slick.thingle.internal.Thinlet#getSelectedItem(java.lang.Object)
	 */
	protected Object getSelectedItem(Object component) {
		return thinlet.getSelectedItem(component);
	}

	/**
	 * @param component
	 * @return
	 * @see org.newdawn.slick.thingle.internal.Thinlet#getSelectedItems(java.lang.Object)
	 */
	protected Object[] getSelectedItems(Object component) {
		return thinlet.getSelectedItems(component);
	}

	/**
	 * @return
	 * @see org.newdawn.slick.thingle.internal.Thinlet#getSize()
	 */
	protected Dimension getSize() {
		return thinlet.getSize();
	}

	/**
	 * @param component
	 * @param key
	 * @return
	 * @see org.newdawn.slick.thingle.internal.Thinlet#getString(java.lang.Object, java.lang.String)
	 */
	protected String getString(Object component, String key) {
		return thinlet.getString(component, key);
	}

	/**
	 * @param component
	 * @param key
	 * @return
	 * @see org.newdawn.slick.thingle.internal.Thinlet#getWidget(java.lang.Object, java.lang.String)
	 */
	protected Object getWidget(Object component, String key) {
		return thinlet.getWidget(component, key);
	}

	/**
	 * @param component
	 * @param name
	 * @return
	 * @see org.newdawn.slick.thingle.internal.Thinlet#find(java.lang.Object, java.lang.String)
	 */
	protected Object find(Object component, String name) {
		return thinlet.find(component, name);
	}

	/**
	 * @param name
	 * @return
	 * @see org.newdawn.slick.thingle.internal.Thinlet#find(java.lang.String)
	 */
	protected Object find(String name) {
		return thinlet.find(name);
	}

	/**
	 * @param parent
	 * @param component
	 * @param index
	 * @see org.newdawn.slick.thingle.internal.Thinlet#add(java.lang.Object, java.lang.Object, int)
	 */
	protected void add(Object parent, Object component, int index) {
		thinlet.add(parent, component, index);
	}

	/**
	 * @param parent
	 * @param component
	 * @see org.newdawn.slick.thingle.internal.Thinlet#add(java.lang.Object, java.lang.Object)
	 */
	protected void add(Object parent, Object component) {
		thinlet.add(parent, component);
	}

	/**
	 * @param component
	 * @see org.newdawn.slick.thingle.internal.Thinlet#add(java.lang.Object)
	 */
	protected void add(Object component) {
		thinlet.add(component);
	}

	/**
	 * @param inputstream
	 * @param handler
	 * @return
	 * @throws IOException
	 * @see org.newdawn.slick.thingle.internal.Thinlet#parse(java.io.InputStream, java.lang.Object)
	 */
	protected Object parse(InputStream inputstream, Object handler) throws IOException {
		return thinlet.parse(inputstream, handler);
	}

	/**
	 * @param inputstream
	 * @return
	 * @throws IOException
	 * @see org.newdawn.slick.thingle.internal.Thinlet#parse(java.io.InputStream)
	 */
	protected Object parse(InputStream inputstream) throws IOException {
		return thinlet.parse(inputstream);
	}

	/**
	 * @param path
	 * @param handler
	 * @return
	 * @throws IOException
	 * @see org.newdawn.slick.thingle.internal.Thinlet#parse(java.lang.String, java.lang.Object)
	 */
	protected Object parse(String path, Object handler) throws IOException {
		return thinlet.parse(path, handler);
	}

	/**
	 * @param path
	 * @return
	 * @throws IOException
	 * @see org.newdawn.slick.thingle.internal.Thinlet#parse(java.lang.String)
	 */
	protected Object parse(String path) throws IOException {
		return thinlet.parse(path);
	}

	/**
	 * @param component
	 * @param key
	 * @param value
	 * @see org.newdawn.slick.thingle.internal.Thinlet#putProperty(java.lang.Object, java.lang.Object, java.lang.Object)
	 */
	protected void putProperty(Object component, Object key, Object value) {
		thinlet.putProperty(component, key, value);
	}

	/**
	 * 
	 * @see org.newdawn.slick.thingle.internal.Thinlet#requestFocus()
	 */
	protected void requestFocus() {
		thinlet.requestFocus();
	}

	/**
	 * @param component
	 * @return
	 * @see org.newdawn.slick.thingle.internal.Thinlet#requestFocus(java.lang.Object)
	 */
	protected boolean requestFocus(Object component) {
		return thinlet.requestFocus(component);
	}

	/**
	 * @param component
	 * @see org.newdawn.slick.thingle.internal.Thinlet#remove(java.lang.Object)
	 */
	protected void remove(Object component) {
		thinlet.remove(component);
	}

	/**
	 * @param component
	 * @see org.newdawn.slick.thingle.internal.Thinlet#removeAll(java.lang.Object)
	 */
	protected void removeAll(Object component) {
		thinlet.removeAll(component);
	}
}
