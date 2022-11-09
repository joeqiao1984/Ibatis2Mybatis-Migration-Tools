package com.github.iut;

import com.github.iut.facade.ConverterFacade;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.w3c.dom.Document;
import org.xml.sax.*;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class Main {
    private static final String LOGO = "\n" +
            "  _ _           _   _      _____                          _            \n" +
            " (_) |         | | (_)    / ____|                        | |           \n" +
            "  _| |__   __ _| |_ _ ___| |     ___  _ ____   _____ _ __| |_ ___ _ __ \n" +
            " | | '_ \\ / _` | __| / __| |    / _ \\| '_ \\ \\ / / _ \\ '__| __/ _ \\ '__|\n" +
            " | | |_) | (_| | |_| \\__ \\ |___| (_) | | | \\ V /  __/ |  | ||  __/ |   \n" +
            " |_|_.__/ \\__,_|\\__|_|___/\\_____\\___/|_| |_|\\_/ \\___|_|   \\__\\___|_|   \n" +
            "                                                                        \n" +
            "                              author:  qiao.joe1984@gmail.com           \n" +
            "                              version: 1.0.0                            \n";
    private static final Logger log = LogManager.getLogger(Main.class);
    public static final String PUBLIC_ID = "-//mybatis.org//DTD Mapper 3.0//EN";
    public static final String SYSTEM_ID = "http://mybatis.org/dtd/mybatis-3-mapper.dtd";
    private static String sourceDir = null;
    private static String destDir = null;

    public static void main(String[] args) throws IOException{
        log.info("{}",LOGO);

        if (args.length != 2) {
            log.error("请输入用于转换的源目录和目标目录，例如：\njava -jar ibatisUpgradeTools.jar c:\\source c:\\target");
            System.exit(1);
        }
        sourceDir = args[0];
        destDir = args[1];
        log.info("开始转换");
        log.info("源目录为:{}",sourceDir);
        log.info("目标目录为:{}",destDir);
        if (!new File(destDir).exists()) {
            new File(destDir).mkdirs();
        }
        convertSqlFiles(sourceDir);
    }

    private static void convertSqlFiles(String targetDir) throws IOException {
        final List<String> typeAliasList = new ArrayList<>();
        final List<Path> targetFiles = Files.walk(Paths.get(targetDir))
                .filter(e -> isXmlFile(e)).collect(Collectors.toList());
        final AtomicInteger i = new AtomicInteger(1);
        targetFiles
                .forEach(e-> {
                    try {
                        convertFile(e, typeAliasList);
                    } catch (ParserConfigurationException | IOException | SAXException | TransformerException ex) {
                        ex.printStackTrace();
                    }
                });
        final String typeAliasStr = typeAliasList.stream().collect(Collectors.joining(System.lineSeparator()));
        log.info("共提取typeAlias信息{}条",typeAliasList.size());
        log.info("typeAlias信息如下：\n{}", typeAliasStr);
        log.info("转换结束,共转换{}个文件,可通过本目录下result.log文件查看转换报错信息和typeAlias记录",targetFiles.size());
    }

    private static void convertFile(final Path e, final List<String> typeAliasList) throws ParserConfigurationException, IOException, SAXException, TransformerException {
        final ConverterFacade converterFacade = new ConverterFacade();
        final DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        documentBuilderFactory.setValidating(true);

        final DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
        documentBuilder.setErrorHandler(new DtdErrorHandler());
        documentBuilder.setEntityResolver(new EntityResolver() {
            @Override
            public InputSource resolveEntity(final String publicId, final String systemId) {
                return new InputSource(getClass().getClassLoader().getResourceAsStream("dtd/sql-map-2.dtd"));
            }
        });
        final File inputFile = e.toFile();
        final Document loadedDocument = documentBuilder.parse(inputFile);
        final Document convertDoc = converterFacade.convert(loadedDocument, inputFile.getName(), typeAliasList);
        final Transformer transformer = TransformerFactory.newInstance().newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.setOutputProperty(OutputKeys.DOCTYPE_PUBLIC,  PUBLIC_ID );
        transformer.setOutputProperty(OutputKeys.DOCTYPE_SYSTEM,  SYSTEM_ID );
        final Path relPath = Paths.get(sourceDir).relativize(Paths.get(inputFile.getAbsolutePath()));
        final String name = destDir + File.separator + relPath;
        log.info(name);
        final String parentDir = new File(name).getParent();
        new File(parentDir).mkdirs();
        final FileOutputStream fileOutputStream = new FileOutputStream(name);
        transformer.transform(new DOMSource(convertDoc), new StreamResult(fileOutputStream));
    }

    private static boolean isXmlFile(final Path e) {
        return e.toFile().isFile() && e.toFile().getName().toLowerCase().endsWith("xml");
    }

    private static class DtdErrorHandler implements ErrorHandler {
        private void logError(SAXParseException exception) {
            log.error("文件[{}]存在XML格式校验错误，内容为[{}]", getFileName(exception), exception.getMessage());
        }

        @Override
        public void warning(final SAXParseException exception) throws SAXException {
            logError(exception);
        }

        @Override
        public void error(final SAXParseException exception) throws SAXException {
            logError(exception);
        }

        @Override
        public void fatalError(final SAXParseException exception) throws SAXException {
            logError(exception);
        }
    }

    private static String getFileName(final SAXParseException exception) {
        try {
            return new File(new URL(exception.getSystemId()).toURI()).getName();
        } catch (URISyntaxException | MalformedURLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
