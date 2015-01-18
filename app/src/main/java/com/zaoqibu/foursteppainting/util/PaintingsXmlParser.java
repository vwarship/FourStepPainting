package com.zaoqibu.foursteppainting.util;

import android.util.Log;

import com.zaoqibu.foursteppainting.domain.Painting;
import com.zaoqibu.foursteppainting.domain.PaintingCategories;
import com.zaoqibu.foursteppainting.domain.PaintingCategory;
import com.zaoqibu.foursteppainting.domain.Paintings;
import com.zaoqibu.foursteppainting.domain.Picture;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by vwarship on 2015/1/16.
 */
public class PaintingsXmlParser {
    public Paintings parse(InputStream inputStream, String categoryCodeName) {
        Paintings paintings = new Paintings();

        try {
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(true);
            XmlPullParser parser = factory.newPullParser();
            parser.setInput(inputStream, null);

            int eventType = parser.getEventType();
            while (eventType != XmlPullParser.END_DOCUMENT) {
                if (eventType == XmlPullParser.START_TAG && parser.getName().equals("painting")) {
                    Painting painting = readPainting(parser, categoryCodeName);

                    for (int order=1; order<=4; ++order) {
                        Picture picture = readPicture(parser, categoryCodeName, painting.getCodeName(), order);
                        painting.add(picture);
                    }

                    paintings.add(painting);
                }

                eventType = parser.next();
            }
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return paintings;
    }

    private Painting readPainting(XmlPullParser parser, String categoryCodeName) throws IOException, XmlPullParserException {
        String name = parser.getAttributeValue(null, "name");
        String codeName = parser.getAttributeValue(null, "codeName");
        String soundPath = String.format("%s/%s/%s.mp3", categoryCodeName, codeName, codeName);

        return new Painting(name, codeName, soundPath);
    }

    private Picture readPicture(XmlPullParser parser, String categoryCodeName, String codeName, int order) throws IOException, XmlPullParserException {
        Picture picture = null;

        int eventType = parser.next();
        while (!(eventType == XmlPullParser.START_TAG && parser.getName().equals("text"))) {
            eventType = parser.next();
        }

        String text = parser.nextText();
        String imagePath = String.format("%s/%s/%d.png", categoryCodeName, codeName, order);
        String soundPath = String.format("%s/%s/%d.mp3", categoryCodeName, codeName, order);

        picture = new Picture(order, imagePath, text, soundPath);

        return picture;
    }

}
