package icucheol.emotion_analyzer.controller;

import icucheol.emotion_analyzer.dto.MemberRequestDto;
import icucheol.emotion_analyzer.dto.MemberResponseDto;
import icucheol.emotion_analyzer.service.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/members")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    /**
     * 회원 등록
     */
    @PostMapping("/register")
    public ResponseEntity<?> registerMember(@Valid @RequestBody MemberRequestDto memberRequestDto) {
        try {
            MemberResponseDto memberResponseDto = memberService.registerMember(memberRequestDto);
            return ResponseEntity.ok(memberResponseDto);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    /**
     * 이메일로 회원 정보 조회
     */
    @GetMapping("/{email}")
    public ResponseEntity<MemberResponseDto> getMemberByEmail(@PathVariable String email) {
        return memberService.findMemberByEmail(email)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * 모든 회원 목록 조회 (관리자 전용)
     */
    @GetMapping
    public ResponseEntity<List<MemberResponseDto>> getAllMembers() {
        List<MemberResponseDto> members = memberService.findAllMembers();
        return ResponseEntity.ok(members);
    }

    /**
     * 회원 삭제
     */
    @DeleteMapping("/{email}")
    public ResponseEntity<Void> deleteMember(@PathVariable String email) {
        try {
            memberService.deleteMemberByEmail(email);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * 비밀번호 변경
     */
    @PatchMapping("/{email}/password")
    public ResponseEntity<Void> changePassword(@PathVariable String email, @RequestBody String newPassword) {
        try {
            memberService.changePassword(email, newPassword);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }
}