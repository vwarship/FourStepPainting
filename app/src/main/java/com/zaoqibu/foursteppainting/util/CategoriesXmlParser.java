package com.zaoqibu.foursteppainting.util;

import com.zaoqibu.foursteppainting.domain.PaintingCategories;
import com.zaoqibu.foursteppainting.domain.PaintingCategory;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by vwarship on 2015/1/15.
 */
public class CategoriesXmlParser {
    public PaintingCategories parse(InputStream inputStream) {
        PaintingCategories paintingCategories = new PaintingCategories();

        try {
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(true);
            XmlPullParser parser = factory.newPullParser();
            parser.setInput(inputStream, null);

            List<PaintingCategory> categories = new ArrayList<PaintingCategory>();

            int eventType = parser.getEventType();
            while (eventType != XmlPullParser.END_DOCUMENT) {
                if (eventType == XmlPullParser.START_TAG && parser.getName().equals("category")) {
                    categories.add(readCategory(parser));
                }

                eventType = parser.next();
            }

            paintingCategories.setDataSource(categories);
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return paintingCategories;
    }

    private PaintingCategory readCategory(XmlPullParser parser) throws IOException, XmlPullParserException {
        String name = parser.getAttributeValue(null, "name");
        String codeName = parser.getAttributeValue(null, "codeName");
        String backgroundColor = parser.getAttributeValue(null, "backgroundColor");

        return new PaintingCategory(name, codeName, backgroundColor);
    }

}
