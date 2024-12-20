package com.nckh.motelroom.controller;

import com.nckh.motelroom.config.JwtConfig;
import com.nckh.motelroom.dto.entity.PostDto;
import com.nckh.motelroom.dto.entity.SearchDto;
import com.nckh.motelroom.filter.JwtTokenFilter;
import com.nckh.motelroom.service.PostService;
import com.nckh.motelroom.service.impl.PostServiceImp;
import com.nckh.motelroom.service.impl.UserDetailServiceImp;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Api(value = "Tìm nhà trọ")
public class PostController {
    private final JwtConfig jwtConfig;

    private final UserDetailServiceImp userDetailServiceImp;

    private final PostServiceImp postService;

    @GetMapping("/post/hello-world")
    public String HelloWolrd(){
        return "Hello World";
    }

    @ApiOperation(value = "Lấy danh sách tin đăng tìm kiếm theo tiêu chí")
    @GetMapping("/posts/search")
    public Page<PostDto> searchPost(SearchDto searchForm, @RequestParam int page, @RequestParam int sort){
        searchForm.setPriceStart(searchForm.getPriceStart()*1000000);
        searchForm.setPriceEnd(searchForm.getPriceEnd()*1000000);
        return postService.searchPost(searchForm, page, sort);
    }

    @ApiOperation(value = "Lấy danh sách tin đăng tìm kiếm xung quanh một vị trí")
    @GetMapping("/posts/searchbymaps")
    public Page<PostDto> searchPostMaps(SearchDto searchForm, @RequestParam int page, @RequestParam int sort){
        searchForm.setPriceStart(searchForm.getPriceStart()*1000000);
        searchForm.setPriceEnd(searchForm.getPriceEnd()*1000000);
        return postService.searchPostByMaps(searchForm, page, sort);
    }

    @ApiOperation(value = "Lấy tất cả tin đăng")
    @GetMapping("/posts")
    public Page<PostDto> getAllPost(@PageableDefault(page = 0, size = 12) Pageable  page) {
        return postService.getAllPost(page);
    }

    @ApiOperation(value = "Lấy danh sách tin đăng đã được duyệt")
    @GetMapping("/posts/approved/true")
    public Page<PostDto> getAllPostApproved(@RequestParam int page) {
        return postService.getPostByApproved(true, page);
    }

    @ApiOperation(value = "Lấy danh sách tin đăng đã bị khóa")
    @GetMapping("/posts/approved/false")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MODERATOR')")
    public Page<PostDto> getAllPostNotApproved(@RequestParam int page) {
        return postService.getPostByApproved(false, page);
    }

    @ApiOperation(value = "Nếu bool = true lấy danh sách tin nhà trọ, ngược lại lấy danh sách tin nhà nguyên căn")
    @GetMapping("/posts/motel/{bool}")
    public Page<PostDto> getMotelPost(@PathVariable boolean bool, @RequestParam int page, @RequestParam int sort) {
        return postService.getMotelPost(bool, page, sort);
    }

    @ApiOperation(value = "Lấy danh sách tin đăng chờ duyệt")
    @GetMapping("/posts/waiting")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MODERATOR')")
    public Page<PostDto> getPostWaitingApprove(@RequestParam int page) {
        return postService.getPostWaitingApprove(page);
    }

    @ApiOperation(value = "Lấy danh sách tin đăng của một người dùng")
    @GetMapping("/post/user/{idUser}")
    public Page<PostDto> getPostByIdUser(@PathVariable long idUser, @RequestParam int page) {
        return postService.getPostByIdUser(idUser, page);
    }

    @ApiOperation(value = "Lấy thông tin của một tin đăng")
    @GetMapping("/post/{id}")
    public PostDto getPostById(@PathVariable Long id) {
        return postService.getPostById(id);
    }

//    @ApiOperation(value = "Đăng tin mới")
//    @PostMapping("/post")
//    public PostDto createPost(@RequestBody PostDto postDTO, @RequestHeader("Authorization") String token) {
//        String userId = jwtConfig.getUserIdFromJWT(token);
//        UserDetails userDetails = userDetailServiceImp.loadUserByUsername(userId);
//        userDetails.getUsername();
////        String username =
//        return postService.createPost(postDTO, "");
//    }

    @ApiOperation(value = "Đăng tin mới")
    @PostMapping("/post")
    public String createPost(@RequestHeader("Authorization") String token) {
        String userId = jwtConfig.getUserIdFromJWT(token);
        UserDetails userDetails = userDetailServiceImp.loadUserByUsername(userId);
        return userDetails.getUsername();
//        String username =
//        return postService.createPost(postDTO, "");
    }

    @ApiOperation(value = "Duyệt/Khóa tin đăng")
    @PutMapping("/post/{id}/approve/{bool}")
    public PostDto ApprovePostAndLogging(@PathVariable Long id, @PathVariable boolean bool) {
        return postService.ApprovePost(id, "USERNAME", bool);
    }

    @ApiOperation(value = "Cập nhật một tin đăng")
    @PutMapping("/post/{id}")
    public PostDto updatePost(@PathVariable Long id, @RequestBody PostDto postDTO) {
        return postService.updatePost(id, postDTO, "USSERNAME");
    }

    @ApiOperation(value = "Ẩn một tin đăng")
    @PutMapping("/post/hide/{id}")
    public PostDto hidePost(@PathVariable Long id) {
        return postService.hidePost(id);
    }

    @ApiOperation(value = "Xóa một tin đăng")
    @DeleteMapping("/post/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MODERATOR')")
    public String deletePostByAdmin(@PathVariable Long id) {
        return postService.deletePostByAdmin(id);
    }

}
