package com.study.springboot.common.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.FileCopyUtils;

import java.io.File;
import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.UUID;

@Slf4j
public class UploadFileUtils {

    public static String uploadFile(String uploadPath, String originalName, byte[] bytes) throws Exception {

        log.debug("UploadFileUtils.uploadFile");

        UUID uid = UUID.randomUUID();

        String savedName = uid.toString() + "_" + originalName;
        String savedPath = calcPath(uploadPath);

        File target = new File(uploadPath + savedPath, savedName);
        FileCopyUtils.copy(bytes, target);

        String uploadedFileName = makeUploadedFileName(uploadPath, savedPath, savedName);

        log.info("uploadedFileName : {}", uploadedFileName);

        return uploadedFileName;
    }

    private static String calcPath(String uploadPath) {

        log.debug("UploadFileUtils.calcPath");

        Calendar cal = Calendar.getInstance();

        String year = File.separator + cal.get(Calendar.YEAR);
        String month = year + File.separator + new DecimalFormat("00").format(cal.get(Calendar.MONTH) + 1);
        String date = month + File.separator + new DecimalFormat("00").format(cal.get(Calendar.DATE));

        makeDir(uploadPath, year, month, date);

        return date;
    }

    private static void makeDir(String uploadPath, String... dirs) {

        log.debug("UploadFileUtils.makeDir");

        if (new File(dirs[dirs.length - 1]).exists()) {
            return;
        }

        for (String dir : dirs) {
            File file = new File(uploadPath + dir);
            if (!file.exists()) {
                file.mkdirs();
            }
        }
    }

    private static String makeUploadedFileName(String uploadPath, String path, String originalName) throws Exception {

        log.debug("UploadFileUtils.makeUploadedFileName");

        String uploadedFileName = uploadPath + path + File.separator + originalName;
        return uploadedFileName.substring(uploadPath.length()).replace(File.separatorChar, '/');
    }

}
