package icucheol.emotion_analyzer.controller;

import icucheol.emotion_analyzer.dto.MemberRequestDto;
import icucheol.emotion_analyzer.dto.MemberResponseDto;
import icucheol.emotion_analyzer.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/members")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    /**
     * 회원 등록
     * @param memberRequestDto 클라이언트 요청 데이터
     * @return 생성된 회원 정보
     */
    @PostMapping("/register")
    public ResponseEntity<MemberResponseDto> registerMember(@RequestBody MemberRequestDto memberRequestDto) {
        try {
            // 서비스 계층에서 회원 등록 처리
            MemberResponseDto memberResponseDto = memberService.registerMember(memberRequestDto);
            return ResponseEntity.ok(memberResponseDto); // 200 OK 응답
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build(); // 400 Bad Request
        }
    }

    /**
     * 이메일을 기준으로 회원 정보 검색
     * @param email 이메일 주소
     * @return 회원 정보 또는 404 상태
     */
    @GetMapping("/{email}")
    public ResponseEntity<MemberResponseDto> getMemberByEmail(@PathVariable String email) {
        return memberService.findMemberByEmail(email)
                .map(ResponseEntity::ok) // 200 OK 응답
                .orElse(ResponseEntity.notFound().build()); // 404 Not Found 응답
    }
}