package net.javaspringboot.kpis_be01.controller;


import net.javaspringboot.kpis_be01.dto.request.CreateNewRoomRequest;
import net.javaspringboot.kpis_be01.dto.request.CreateUserRequest;
import net.javaspringboot.kpis_be01.dto.response.ApiResponse;
import net.javaspringboot.kpis_be01.entity.*;
import net.javaspringboot.kpis_be01.service.AssessmentService;
import net.javaspringboot.kpis_be01.service.UserSevice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static net.javaspringboot.kpis_be01.configuration.checkRoleAccount.hasRole;

@RestController
@RequestMapping("/admin")
public class AccountController {

    @Autowired
    AssessmentService assessmentService;
    @Autowired
    UserSevice userSevice;

    public List<User> getListUser(){
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        Staffs staffs = assessmentService.getStaffByUserName(authentication.getName()).get();

        List<User> usersList= userSevice.getAllUser();
        Iterator<User> iterator = usersList.iterator();
        while (iterator.hasNext()) {
            User u=iterator.next();
            if(u.getUsername().equals(staffs.getUsername().getUsername())){
                iterator.remove();
            }
            if(!hasRole("Admin")&& u.getRole_name().getRolename().equalsIgnoreCase("admin")){
                iterator.remove();
            }
        }
        return usersList;

    }

    //Quản lý User
    @GetMapping("/getAllUser")
    public ApiResponse<List<User>> getAllUser(){
        List<User> users=new ArrayList<>();
        if(hasRole("Admin")){
            users=userSevice.getAllUser();
        }
        else {
            users=getListUser();
        }
        return  ApiResponse.<List<User>>builder()
                .message("SUCCESS")
                .code(1000)
                .result(users)
                .build();
    }

    //Quản lý Staff
    @GetMapping("/getAllStaff")
    public  ApiResponse<List<Staffs>> getAllStaff(){
        List<Staffs> staffs=assessmentService.getAllStaff();
        Iterator<Staffs> iterator = staffs.iterator();
        while (iterator.hasNext()) {
            Staffs s = iterator.next();
            String role_name = s.getUsername().getRole_name().getRolename();
            if (role_name.equalsIgnoreCase("admin")
                    || role_name.equalsIgnoreCase("input")
                    || role_name.equalsIgnoreCase("authorizer")){
                iterator.remove();
            }
        }
        return  ApiResponse.<List<Staffs>>builder()
                .code(1000)
                .message("SUCCESS")
                .result(staffs)
                .build();
    }

    //Quản lý khoa/phòng
    @GetMapping("/getAllRoom")
    public  ApiResponse<List<RoomType>> getAllRoom(){
        List<RoomType> roomTypes=assessmentService.getAllRoomType();

        return  ApiResponse.<List<RoomType>>builder()
                .message("SUCCESS")
                .result(roomTypes)
                .code(1000)
                .build();
    }

    //Xuất file Excel(User)
    //Thêm mới(User)
    @PostMapping("/SaveAccount")
    public ApiResponse<String> SaveAccount(@RequestBody CreateUserRequest createUserRequest){
        List<User> users=userSevice.getAllUser();
        if(createUserRequest.getEmail()==null|| createUserRequest.getEmail().isBlank()){
            createUserRequest.setEmail("None");
        }
        for(User u:users){
            if(createUserRequest.getUsername().equalsIgnoreCase(u.getUsername())||((!createUserRequest.getEmail().equalsIgnoreCase("none") && createUserRequest.getEmail().equalsIgnoreCase(u.getEmail())))){
                return ApiResponse.<String>builder()
                        .message("This username or email had already in data list!")
                        .code(1100)
                        .build();
            }
        }
        if(createUserRequest.getPassword().isBlank()){
            return  ApiResponse.<String>builder()
                    .message("Password is blank!")
                    .code(1100)
                    .build();
        }
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodePassword = passwordEncoder.encode(createUserRequest.getPassword());
        createUserRequest.setPassword(encodePassword);
        createUserRequest.setStatus(createUserRequest.isStatus());
        User user = getUser(createUserRequest);
        userSevice.SaveorUpdate(user);
        UserInRole userInRole = new UserInRole(user.getId(), createUserRequest.getRole_id());
        //thiếu auto add to staff list after create a user
        assessmentService.SaveOrUpdateRole(userInRole);
        return  ApiResponse.<String>builder()
                .code(1000)
                .message("SUCCESS")
                .build();
    }

    public  User getUser(CreateUserRequest createUserRequest) {
        User user=new User();
        user.setFullname(createUserRequest.getFullname());
        user.setPassword(createUserRequest.getPassword());
        user.setUsername(createUserRequest.getUsername());
        user.setEmail(createUserRequest.getEmail());
        user.setRank_code(assessmentService.getRankStaff(createUserRequest.getRole_id()));
        user.setGroup_work(createUserRequest.getGroup_work());
        user.setRole_name(assessmentService.getRoleById(createUserRequest.getRole_id()));
        user.setRoom_type(assessmentService.getRoomTypeById(createUserRequest.getRoom_type_ID()));
        user.setStatus(createUserRequest.isStatus());
        user.setCreated_at(Date.valueOf(LocalDate.now()));
        return user;
    }
    //Edit(User)
    //Reset Password(User)

    //Xuất file Excel(Staffs)
    //Thêm mới(Staff)


    //Thêm mới(khoa/phòng)

    //bên FE sẽ lấy data user vào màn hình add new room và phân quyền trong đó
    @GetMapping("/getAllForSave")
    public ApiResponse<List<User>> getALlDirector(){
        List<User> users=assessmentService.getAllUsersByRole("Director");
        users.add(userSevice.getUserByUsername("input"));
        users.add(userSevice.getUserByUsername("admin"));
        users.add(userSevice.getUserByUsername("authorizer"));
        return ApiResponse.<List<User>>builder()
                .message("SUCCESS")
                .code(1000)
                .result(users)
                .build();
    }

    @PostMapping("/saveNewRoom")
    public ApiResponse<String> saveNewRoom(@RequestBody CreateNewRoomRequest request){
        var authentication= SecurityContextHolder.getContext().getAuthentication();
        Staffs staffs= assessmentService.getStaffByUserName(authentication.getName()).get();
        RoomType roomType=assessmentService.getRoomTypeBySymbol(request.getRoom_symbol());

        if(roomType!=null){
            return  ApiResponse.<String>builder()
                    .message("This room had already been added!")
                    .code(1100)
                    .build();
        }
        RoomType newRomType=new RoomType();
        newRomType.setUser(userSevice.getUserNameByUniqueName(request.getUnique_username()));
        newRomType.setUnique_username(request.getUnique_username());
        newRomType.setRoom_name(request.getRoom_name());
        newRomType.setRoom_symbol(request.getRoom_symbol());
        newRomType.setCreated_at(Date.valueOf(LocalDate.now()));
        newRomType.setCreated_by(staffs.getUsername().getUsername());

        assessmentService.SaveOrUpdateRoom(newRomType);

        return ApiResponse.<String>builder()
                .code(1000)
                .message("SUCCESS")
                .build();
    }
    //Edit(khoa/phòng)

    @GetMapping("/getAllForEdit")
    public  ApiResponse<List<User>> getAllForEdit(){
        List<User> users=assessmentService.getAllUsersByRole("Director");
        users.addAll(assessmentService.getAllUsersByRole("Vice_Director"));
        users.add(userSevice.getUserByUsername("input"));
        users.add(userSevice.getUserByUsername("admin"));
        users.add(userSevice.getUserByUsername("authorizer"));
        return ApiResponse.<List<User>>builder()
                .message("SUCCESS")
                .code(1000)
                .result(users)
                .build();
    }

    public List<RoomType> getRoomType(RoomType roomType){
        List<RoomType> roomTypes=assessmentService.showAllRoom();
        Iterator<RoomType> iterator = roomTypes.iterator();
        while (iterator.hasNext()){
            RoomType r = iterator.next();
            if (r.getRoom_symbol().equalsIgnoreCase(roomType.getRoom_symbol())){
                iterator.remove();
            }
        }
        return roomTypes;
    }

    @PostMapping("/saveEditRoom/{room_id}")
    public ApiResponse<String> saveEditRoom(@PathVariable("room_id")  Long room_id,  @RequestBody CreateNewRoomRequest request){
        var authentication= SecurityContextHolder.getContext().getAuthentication();
        Staffs staffs= assessmentService.getStaffByUserName(authentication.getName()).get();
        RoomType oldRoomType=assessmentService.getRoomTypeBySymbol(request.getRoom_symbol());
        List<RoomType> roomTypeList=getRoomType(oldRoomType);
        for(RoomType r:roomTypeList){
            if(request.getRoom_symbol().equalsIgnoreCase(r.getRoom_symbol())){
                return  ApiResponse.<String>builder()
                        .code(1100)
                        .message("This room code had already been added!")
                        .build();
            }
        }
        List<User>userList=userSevice.getAllUser();
        for (User u:userList){
            if(u.getRoom_type().getRoom_symbol().equalsIgnoreCase(oldRoomType.getRoom_symbol())){
                return  ApiResponse.<String>builder()
                        .code(1100)
                        .message("Error, can't edit this room because it was added data to User and Staff room")
                        .build();
            }
        }
        RoomType newRoom=assessmentService.getRoomTypeById(room_id);
        newRoom.setUser(userSevice.getUserNameByUniqueName(request.getUnique_username()));
        newRoom.setRoom_name(request.getRoom_name());
        newRoom.setRoom_symbol(request.getRoom_symbol());
        newRoom.setCreated_at(Date.valueOf(LocalDate.now()));
        newRoom.setCreated_by(staffs.getUsername().getUsername());
        assessmentService.SaveOrUpdateRoom(newRoom);

        return ApiResponse.<String>builder()
                .code(1000)
                .message("SUCCESS")
                .build();
    }
}
