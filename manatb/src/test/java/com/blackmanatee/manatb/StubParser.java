package com.blackmanatee.manatb;

import android.content.res.XmlResourceParser;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.util.ArrayDeque;
import java.util.Vector;

/**
 * Created by DeCorben on 1/12/2018.
 */

public class StubParser implements XmlResourceParser {
    private ArrayDeque<ParseEvent> stack;

    public StubParser(String d){
        stack = new ArrayDeque<>();
        stack.add(new ParseEvent(XmlPullParser.START_DOCUMENT,"",""));
        String[] ay = d.split("<");
        for(String bee:ay){
            String[] cee = bee.split(">");
            if(cee[0].trim().length() > 0){
                String dee = cee[0].trim();
                //parse tag
                //no attributes right now
                //no empty tags right now
                if(dee.charAt(0) != '?'){
                    if(dee.charAt(0) == '/')
                        stack.add(new ParseEvent(XmlPullParser.END_TAG,dee.substring(1),""));
                    else
                        stack.add(new ParseEvent(XmlPullParser.START_TAG,dee,""));
                }
            }
            if(cee.length > 1 && cee[1].trim().length() > 0){
                //parse text
                stack.add(new ParseEvent(XmlPullParser.TEXT,"",cee[1].trim()));
            }
        }
        stack.add(new ParseEvent(XmlPullParser.END_DOCUMENT,"",""));
    }

    @Override
    public int next() throws XmlPullParserException, IOException {
        stack.pop();
        return stack.peek().type;
    }

    @Override
    public String getName() {
        return stack.peek().name;
    }

    @Override
    public String getText() {
        return stack.peek().text;
    }

    private class ParseEvent{
        public int type;
        public String name;
        public String text;

        public ParseEvent(){
            type = -1;
            name = "";
            text = "";
        }

        public ParseEvent(int i,String n,String t){
            type = i;
            name = n;
            text = t;
        }
    }

    @Override
    public int getEventType() throws XmlPullParserException {
        return stack.peek().type;
    }

    //THE FOLD

    @Override
    public void close() {

    }

    @Override
    public int getAttributeNameResource(int index) {
        return 0;
    }

    @Override
    public int getAttributeListValue(String namespace, String attribute, String[] options, int defaultValue) {
        return 0;
    }

    @Override
    public boolean getAttributeBooleanValue(String namespace, String attribute, boolean defaultValue) {
        return false;
    }

    @Override
    public int getAttributeResourceValue(String namespace, String attribute, int defaultValue) {
        return 0;
    }

    @Override
    public int getAttributeIntValue(String namespace, String attribute, int defaultValue) {
        return 0;
    }

    @Override
    public int getAttributeUnsignedIntValue(String namespace, String attribute, int defaultValue) {
        return 0;
    }

    @Override
    public float getAttributeFloatValue(String namespace, String attribute, float defaultValue) {
        return 0;
    }

    @Override
    public int getAttributeListValue(int index, String[] options, int defaultValue) {
        return 0;
    }

    @Override
    public boolean getAttributeBooleanValue(int index, boolean defaultValue) {
        return false;
    }

    @Override
    public int getAttributeResourceValue(int index, int defaultValue) {
        return 0;
    }

    @Override
    public int getAttributeIntValue(int index, int defaultValue) {
        return 0;
    }

    @Override
    public int getAttributeUnsignedIntValue(int index, int defaultValue) {
        return 0;
    }

    @Override
    public float getAttributeFloatValue(int index, float defaultValue) {
        return 0;
    }

    @Override
    public String getIdAttribute() {
        return null;
    }

    @Override
    public String getClassAttribute() {
        return null;
    }

    @Override
    public int getIdAttributeResourceValue(int defaultValue) {
        return 0;
    }

    @Override
    public int getStyleAttribute() {
        return 0;
    }

    @Override
    public void setFeature(String name, boolean state) throws XmlPullParserException {

    }

    @Override
    public boolean getFeature(String name) {
        return false;
    }

    @Override
    public void setProperty(String name, Object value) throws XmlPullParserException {

    }

    @Override
    public Object getProperty(String name) {
        return null;
    }

    @Override
    public void setInput(Reader in) throws XmlPullParserException {

    }

    @Override
    public void setInput(InputStream inputStream, String inputEncoding) throws XmlPullParserException {

    }

    @Override
    public String getInputEncoding() {
        return null;
    }

    @Override
    public void defineEntityReplacementText(String entityName, String replacementText) throws XmlPullParserException {

    }

    @Override
    public int getNamespaceCount(int depth) throws XmlPullParserException {
        return 0;
    }

    @Override
    public String getNamespacePrefix(int pos) throws XmlPullParserException {
        return null;
    }

    @Override
    public String getNamespaceUri(int pos) throws XmlPullParserException {
        return null;
    }

    @Override
    public String getNamespace(String prefix) {
        return null;
    }

    @Override
    public int getDepth() {
        return 0;
    }

    @Override
    public String getPositionDescription() {
        return null;
    }

    @Override
    public int getLineNumber() {
        return 0;
    }

    @Override
    public int getColumnNumber() {
        return 0;
    }

    @Override
    public boolean isWhitespace() throws XmlPullParserException {
        return false;
    }

    @Override
    public char[] getTextCharacters(int[] holderForStartAndLength) {
        return new char[0];
    }

    @Override
    public String getNamespace() {
        return null;
    }

    @Override
    public String getPrefix() {
        return null;
    }

    @Override
    public boolean isEmptyElementTag() throws XmlPullParserException {
        return false;
    }

    @Override
    public int getAttributeCount() {
        return 0;
    }

    @Override
    public String getAttributeNamespace(int index) {
        return null;
    }

    @Override
    public String getAttributeName(int index) {
        return null;
    }

    @Override
    public String getAttributePrefix(int index) {
        return null;
    }

    @Override
    public String getAttributeType(int index) {
        return null;
    }

    @Override
    public boolean isAttributeDefault(int index) {
        return false;
    }

    @Override
    public String getAttributeValue(int index) {
        return null;
    }

    @Override
    public String getAttributeValue(String namespace, String name) {
        return null;
    }

    @Override
    public int nextToken() throws XmlPullParserException, IOException {
        return 0;
    }

    @Override
    public void require(int type, String namespace, String name) throws XmlPullParserException, IOException {

    }

    @Override
    public String nextText() throws XmlPullParserException, IOException {
        return null;
    }

    @Override
    public int nextTag() throws XmlPullParserException, IOException {
        return 0;
    }
}
