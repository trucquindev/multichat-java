package com.example.MultiChat.group;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/group")
public class GroupController {

    @Autowired
    private GroupRepository groupRepo;
    @PostMapping
    public ResponseEntity<Group> createGroup(@RequestBody CreateGroupRequest request) {
        Group group = new Group();
        group.setName(request.getName());
        group.setCreatedBy(request.getCreatorId());

        // Thêm người tạo vào nhóm luôn
        List<String> members = new ArrayList<>(request.getMemberIds());
        if (!members.contains(request.getCreatorId())) {
            members.add(request.getCreatorId());
        }

        group.setMemberIds(members);

        Group saved = groupRepo.save(group);
        return ResponseEntity.ok(saved);
    }

    @GetMapping
    public List<Group> getMyGroups(HttpServletRequest request) {
        String userId = (String) request.getAttribute("userId");
        return groupRepo.findByMemberIdsContaining(userId);
    }

//    @PostMapping("/{groupId}/add")
//    public Group addMember(@PathVariable String groupId, @RequestBody String memberId) {
//        Group group = groupRepo.findById(groupId).orElseThrow();
//        if (!group.getMemberIds().contains(memberId)) {
//            group.getMemberIds().add(memberId);
//        }
//        return groupRepo.save(group);
//    }
// Thêm thành viên vào nhóm
    @PostMapping("/{groupId}/members")
    public ResponseEntity<Group> addMembers(@PathVariable String groupId, @RequestBody List<String> newMembers) {
        Optional<Group> optional = groupRepo.findById(groupId);
        if (optional.isPresent()) {
            Group group = optional.get();
            Set<String> allMembers = new HashSet<>(group.getMemberIds());
            allMembers.addAll(newMembers);
            group.setMemberIds(new ArrayList<>(allMembers));
            Group saved = groupRepo.save(group);
            return ResponseEntity.ok(saved);
        }
        return ResponseEntity.notFound().build();
    }

    // Lấy danh sách nhóm mà user đang tham gia
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Group>> getGroupsByUser(@PathVariable String userId) {
        List<Group> groups = groupRepo.findByMemberIdsContaining(userId);
        return ResponseEntity.ok(groups);
    }

    @PostMapping("/{groupId}/remove")
    public Group removeMember(@PathVariable String groupId, @RequestBody String memberId) {
        Group group = groupRepo.findById(groupId).orElseThrow();
        group.getMemberIds().remove(memberId);
        return groupRepo.save(group);
    }
}
