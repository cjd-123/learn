package com.cjd.controller;


import com.cjd.pojo.Download;
import com.cjd.service.DownloadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author cjd
 * @since 2021-04-17
 */
@Controller
public class DownloadController {
    @Autowired
    DownloadService downloadService;

    @GetMapping("/download")
    public String download(Model model){
        List<Download> list = downloadService.list(null);
        model.addAttribute("downloadList",list);
        return "page/download";
    }
}

