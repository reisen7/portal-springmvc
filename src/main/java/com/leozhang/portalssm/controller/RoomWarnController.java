package com.leozhang.portalssm.controller;

import com.github.pagehelper.PageHelper;
import com.leozhang.portalssm.entity.Room;
import com.leozhang.portalssm.entity.RoomWarn;
import com.leozhang.portalssm.service.RoomService;
import com.leozhang.portalssm.service.RoomWarnService;
import com.leozhang.portalssm.util.Result;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/room-warn")
public class RoomWarnController {

    @Autowired
    private RoomWarnService roomWarnService;

    @Autowired
    private RoomService roomService;

    @RequiresPermissions(value = {"permission:query"})
    @RequestMapping("/list")
    public String roomWarnList(){
        return "room/room-warn/list";
    }

    @RequestMapping("/list/page")
    @ResponseBody
    public Result roomWarnListPage(@RequestParam(value = "pno",defaultValue = "1")int pno,
                                   @RequestParam(value = "psize",defaultValue = "10")int psize,
                                   @RequestParam(value = "sortField",defaultValue = "")String sortField,
                                   @RequestParam(value = "sortType",defaultValue = "")String sortType
                                   ){

        return roomWarnService.selectListForPage(pno,psize,sortField,sortType);
    }

    @RequestMapping("/edit/page")
    public String RoomWarnEditPage(Long roomId, Model model){
        Room room = roomService.selectById(roomId);
        RoomWarn roomWarn = roomWarnService.getRoomWarnByRoomId(roomId);
        model.addAttribute("formData",roomWarn);
        model.addAttribute("room",room);
        return "room/room-warn/edit";
    }

    @RequestMapping("/edit")
    public String roomWarnEdit(RoomWarn roomWarn){
        roomWarnService.update(roomWarn);
        return "redirect:/room-warn/list";
    }
}
