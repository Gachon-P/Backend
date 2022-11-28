# Backend

# github commit rules
### 제목을 아랫줄에 작성, 제목 끝에 마침표 금지, 무엇을 했는지 명확하게 작성

> example <br>
> Feature:  Add Download Images From Met. Museum

## 1. <i>Type list</i>
```
#  Feature : 새로운 기능 추가
#  Fix : 버그 수정
#  Docs : 문서 수정
#  Test : 테스트 코드 추가
#  Refactor : 코드 리팩토링
#  Style : 코드 의미에 영향을 주지 않는 변경사항
#  Chore : 빌드 부분 혹은 패키지 매니저 수정사항
```

## 2. <i>Rules</i>
```
#  <Commit Comment Convention(C.C.C.)>
#  제목줄 첫글자 대문자로 작성
#  <Type>을 동사원형으로 작성(Fixed -> Fix, Testing -> Test)
#  제목줄은 마침표로 끝내지 않는다.
#  본문과 제목에는 빈줄을 넣어서 구분한다.
#  본문에는 "어떻게"(not How) 보다는 "왜"(Why)와 "무엇을"(What) 설명한다.
#  본문에 여러 사항 변경을 나타낼때는 "-"로 시작한다.
#  본문(추가 설명)을 아랫줄에 작성
#  각 행은 72자로 제한
```

## 3. <i>git Issue Close comment</i>
```
#  Git Issue Close 코멘트
#  <키워드> #<이슈번호> <코멘트>
#  close, closes, closed -- 일반 개발 관련 이슈
#  fix, fixed -- 버그 Fix 관련 이슈
#  resolve, resolves, resolved -- 문의사항 이슈
```

## 4. <i>Jira Issue KeyWord 코멘트</i>
```
#  <Jira 이슈번호> #<키워드> #comment <코멘트>
ex) GAC-6 #in-progress #comment Metropolitan Museum 소장 작품 이미지 다운로드 구현
```
