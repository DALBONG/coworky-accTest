-- data.sql (최종 안전 버전: keywords 컬럼 및 다중 행 삽입 구문 제거)

-- 1. TB_Company 데이터
INSERT INTO `TB_Company` (connected_date, company_name, status)
VALUES (NOW(), 'test', 'CONNECTED');

-- 2. TB_User 데이터
INSERT INTO `TB_User` (login_id, login_pwd, name, role, company_id)
VALUES ('admin', '1234', 'admin', 'ADMIN', 1);

-- =========================
-- 3. TB_Chatbot 데이터 (50개) - keywords 컬럼 포함 (8개 컬럼)
-- =========================

-- NOTICE (20개)
INSERT INTO `TB_Chatbot` (ctype, title, content, keywords, status, user_id, created_date, updated_date) VALUES ('NOTICE', '워케이션 센터 정기 점검 안내', '시설 점검으로 인한 임시 중단 안내드립니다. 이용 시 참고 바랍니다.', '점검, 시설, 중단, 공지, 이용', 'EXIST', 1, NOW(), NOW());
INSERT INTO `TB_Chatbot` (ctype, title, content, keywords, status, user_id, created_date, updated_date) VALUES ('NOTICE', '신규 워케이션 시설 오픈 안내', '새로운 워케이션 공간이 개설되었습니다. 많은 이용 바랍니다.', '신규, 오픈, 시설, 공간, 개설', 'EXIST', 1, NOW(), NOW());
INSERT INTO `TB_Chatbot` (ctype, title, content, keywords, status, user_id, created_date, updated_date) VALUES ('NOTICE', '회의실 예약 시스템 업데이트 공지', '예약 시스템이 개선되었으며 일부 기능이 변경되었습니다.', '회의실, 예약, 시스템, 업데이트, 변경', 'EXIST', 1, NOW(), NOW());
INSERT INTO `TB_Chatbot` (ctype, title, content, keywords, status, user_id, created_date, updated_date) VALUES ('NOTICE', '공용 휴게실 이용 규정 변경', '휴게실 이용 규정이 변경되었습니다. 상세 내용은 공지사항을 확인하세요.', '휴게실, 이용, 규정, 변경, 공지', 'EXIST', 1, NOW(), NOW());
INSERT INTO `TB_Chatbot` (ctype, title, content, keywords, status, user_id, created_date, updated_date) VALUES ('NOTICE', '주차장 공사 일정 안내', '주차장 보수 공사가 진행될 예정이오니 이용 시 참고 바랍니다.', '주차장, 공사, 보수, 일정, 참고', 'EXIST', 1, NOW(), NOW());
INSERT INTO `TB_Chatbot` (ctype, title, content, keywords, status, user_id, created_date, updated_date) VALUES ('NOTICE', '냉난방 설비 점검 안내', '워크센터 냉난방 설비 점검이 예정되어 있습니다.', '냉난방, 설비, 점검, 워크센터', 'EXIST', 1, NOW(), NOW());
INSERT INTO `TB_Chatbot` (ctype, title, content, keywords, status, user_id, created_date, updated_date) VALUES ('NOTICE', '주말 시설 운영 시간 변경 안내', '주말 운영 시간이 변경되었으니 예약 시 확인 바랍니다.', '주말, 운영시간, 변경, 예약, 확인', 'EXIST', 1, NOW(), NOW());
INSERT INTO `TB_Chatbot` (ctype, title, content, keywords, status, user_id, created_date, updated_date) VALUES ('NOTICE', '워크숍 대관 서비스 제공 시작', '대형 워크숍 공간 대관 서비스가 신규 추가되었습니다.', '워크숍, 대관, 서비스, 공간, 신규', 'EXIST', 1, NOW(), NOW());
INSERT INTO `TB_Chatbot` (ctype, title, content, keywords, status, user_id, created_date, updated_date) VALUES ('NOTICE', '카페테리아 운영시간 연장 안내', '카페테리아 운영시간이 연장되어 더욱 편하게 이용하실 수 있습니다.', '카페테리아, 운영시간, 연장, 이용', 'EXIST', 1, NOW(), NOW());
INSERT INTO `TB_Chatbot` (ctype, title, content, keywords, status, user_id, created_date, updated_date) VALUES ('NOTICE', '안내데스크 위치 변경 안내', '안내데스크가 1층 로비로 변경되었습니다.', '안내데스크, 위치, 변경, 로비, 1층', 'EXIST', 1, NOW(), NOW());
INSERT INTO `TB_Chatbot` (ctype, title, content, keywords, status, user_id, created_date, updated_date) VALUES ('NOTICE', '야외 워케이션 존 운영 재개 안내', '점검으로 중단되었던 야외 워케이션 시설이 재개 운영됩니다.', '야외, 재개, 운영, 시설, 점검', 'EXIST', 1, NOW(), NOW());
INSERT INTO `TB_Chatbot` (ctype, title, content, keywords, status, user_id, created_date, updated_date) VALUES ('NOTICE', '신규 직원 대상 워케이션 설명회', '신규 직원의 워케이션 이용 안내를 위한 설명회가 진행됩니다.', '신규 직원, 설명회, 이용 안내', 'EXIST', 1, NOW(), NOW());
INSERT INTO `TB_Chatbot` (ctype, title, content, keywords, status, user_id, created_date, updated_date) VALUES ('NOTICE', '사물함 신규 설치 안내', '이용자 편의를 위해 사물함이 추가 설치되었습니다.', '사물함, 설치, 추가, 편의', 'EXIST', 1, NOW(), NOW());
INSERT INTO `TB_Chatbot` (ctype, title, content, keywords, status, user_id, created_date, updated_date) VALUES ('NOTICE', '카페테리아 리뉴얼 안내', '카페테리아 리뉴얼이 완료되었습니다.', '카페테리아, 리뉴얼, 완료', 'EXIST', 1, NOW(), NOW());
INSERT INTO `TB_Chatbot` (ctype, title, content, keywords, status, user_id, created_date, updated_date) VALUES ('NOTICE', '특별 워케이션 이벤트 안내', '특별 이벤트가 진행됩니다. 신청은 포털에서 가능합니다.', '이벤트, 특별, 신청, 포털', 'EXIST', 1, NOW(), NOW());
INSERT INTO `TB_Chatbot` (ctype, title, content, keywords, status, user_id, created_date, updated_date) VALUES ('NOTICE', '예약 알림 서비스 업데이트', '예약 알림 기능이 개선되었습니다.', '예약, 알림, 서비스, 기능 개선', 'EXIST', 1, NOW(), NOW());
INSERT INTO `TB_Chatbot` (ctype, title, content, keywords, status, user_id, created_date, updated_date) VALUES ('NOTICE', '회의실 모니터 교체 안내', '회의실 모니터가 전면 교체됩니다.', '회의실, 모니터, 교체', 'EXIST', 1, NOW(), NOW());
INSERT INTO `TB_Chatbot` (ctype, title, content, keywords, status, user_id, created_date, updated_date) VALUES ('NOTICE', '야간 이용 규정 강화 안내', '야간 시설 이용 규정이 강화되었습니다.', '야간, 이용, 규정, 강화', 'EXIST', 1, NOW(), NOW());
INSERT INTO `TB_Chatbot` (ctype, title, content, keywords, status, user_id, created_date, updated_date) VALUES ('NOTICE', '반려동물 동반 이용 불가 안내', '시설 내 반려동물 동반이 제한됩니다.', '반려동물, 동반, 불가, 제한, 시설', 'EXIST', 1, NOW(), NOW());
INSERT INTO `TB_Chatbot` (ctype, title, content, keywords, status, user_id, created_date, updated_date) VALUES ('NOTICE', '워크숍 예약 서비스 오픈', '워크숍용 대형 회의실 예약 기능이 신규 제공됩니다.', '워크숍, 예약, 대형 회의실, 서비스', 'EXIST', 1, NOW(), NOW());

-- FAQ (20개)
INSERT INTO `TB_Chatbot` (ctype, title, content, keywords, status, user_id, created_date, updated_date) VALUES ('FAQ', '[질문] 워케이션 시설 예약은 어떻게 하나요?', '[답변] 예약 시스템에 로그인 후 원하는 날짜와 공간을 선택하시면 됩니다.', '예약, 시간, 공간, 로그인, 시설', 'EXIST', 1, NOW(), NOW());
INSERT INTO `TB_Chatbot` (ctype, title, content, keywords, status, user_id, created_date, updated_date) VALUES ('FAQ', '[질문] 예약 취소는 어디서 가능한가요?', '[답변] 마이페이지의 예약 내역에서 직접 취소할 수 있습니다.', '예약, 취소, 마이페이지, 내역', 'EXIST', 1, NOW(), NOW());
INSERT INTO `TB_Chatbot` (ctype, title, content, keywords, status, user_id, created_date, updated_date) VALUES ('FAQ', '[질문] 시설 이용 요금은 있나요?', '[답변] 기본 워케이션 공간은 무료이며 일부 부가 서비스만 유료입니다.', '이용 요금, 무료, 유료, 부가 서비스', 'EXIST', 1, NOW(), NOW());
INSERT INTO `TB_Chatbot` (ctype, title, content, keywords, status, user_id, created_date, updated_date) VALUES ('FAQ', '[질문] 회의실 이용은 별도 신청해야 하나요?', '[답변] 회의실은 사전 예약이 필요합니다.', '회의실, 신청, 예약, 사전', 'EXIST', 1, NOW(), NOW());
INSERT INTO `TB_Chatbot` (ctype, title, content, keywords, status, user_id, created_date, updated_date) VALUES ('FAQ', '[질문] 주차는 어떻게 이용하나요?', '[답변] 입주 회원은 무료이며 방문객은 등록 후 유료 이용 가능합니다.', '주차, 이용, 무료, 유료, 방문객', 'EXIST', 1, NOW(), NOW());
INSERT INTO `TB_Chatbot` (ctype, title, content, keywords, status, user_id, created_date, updated_date) VALUES ('FAQ', '[질문] 와이파이 비밀번호는 무엇인가요?', '[답변] 안내 데스크 또는 시설 내 안내판에서 확인할 수 있습니다.', '와이파이, 비밀번호, 안내 데스크, 안내판', 'EXIST', 1, NOW(), NOW());
INSERT INTO `TB_Chatbot` (ctype, title, content, keywords, status, user_id, created_date, updated_date) VALUES ('FAQ', '[질문] 사물함은 어떻게 신청하나요?', '[답변] 모바일 앱에서 신청하거나 안내 데스크에서 등록 가능합니다.', '사물함, 신청, 모바일 앱, 안내 데스크', 'EXIST', 1, NOW(), NOW());
INSERT INTO `TB_Chatbot` (ctype, title, content, keywords, status, user_id, created_date, updated_date) VALUES ('FAQ', '[질문] 프린터 이용은 무료인가요?', '[답변] 흑백 출력은 무료이며 컬러 출력은 별도 요금이 부과됩니다.', '프린터, 출력, 무료, 흑백, 컬러, 요금', 'EXIST', 1, NOW(), NOW());
INSERT INTO `TB_Chatbot` (ctype, title, content, keywords, status, user_id, created_date, updated_date) VALUES ('FAQ', '[질문] 운영시간이 궁금해요.', '[답변] 평일은 오전 9시부터 오후 7시까지 운영됩니다.', '운영시간, 평일, 시간', 'EXIST', 1, NOW(), NOW());
INSERT INTO `TB_Chatbot` (ctype, title, content, keywords, status, user_id, created_date, updated_date) VALUES ('FAQ', '[질문] 개인 장비 반입이 가능한가요?', '[답변] 개인 노트북, 모니터 등은 자유롭게 반입 가능합니다.', '개인 장비, 노트북, 모니터, 반입', 'EXIST', 1, NOW(), NOW());
INSERT INTO `TB_Chatbot` (ctype, title, content, keywords, status, user_id, created_date, updated_date) VALUES ('FAQ', '[질문] 카페테리아 이용 요금은 얼마인가요?', '[답변] 커피와 차는 무료로 제공되며, 식사 메뉴는 유료입니다.', '카페테리아, 이용 요금, 무료, 유료, 식사, 커피, 차', 'EXIST', 1, NOW(), NOW());
INSERT INTO `TB_Chatbot` (ctype, title, content, keywords, status, user_id, created_date, updated_date) VALUES ('FAQ', '[질문] 분실물은 어디로 문의해야 하나요?', '[답변] 분실물은 안내 데스크로 문의해 주시면 됩니다.', '분실물, 문의, 안내 데스크', 'EXIST', 1, NOW(), NOW());
INSERT INTO `TB_Chatbot` (ctype, title, content, keywords, status, user_id, created_date, updated_date) VALUES ('FAQ', '[질문] 스마트락이 열리지 않아요.', '[답변] 앱의 스마트키를 새로고침하거나 안내 데스크에 문의하세요.', '스마트락, 앱, 스마트키, 오류, 문의', 'EXIST', 1, NOW(), NOW());
INSERT INTO `TB_Chatbot` (ctype, title, content, keywords, status, user_id, created_date, updated_date) VALUES ('FAQ', '[질문] 시설 내에서 통화해도 되나요?', '[답변] 공용 공간에서는 자제 부탁드리며, 폰 부스를 이용해 주세요.', '통화, 공용 공간, 폰 부스, 이용', 'EXIST', 1, NOW(), NOW());
INSERT INTO `TB_Chatbot` (ctype, title, content, keywords, status, user_id, created_date, updated_date) VALUES ('FAQ', '[질문] 재활용 쓰레기 분리수거는 어떻게 하나요?', '[답변] 각 층에 비치된 분리수거함을 이용해 주십시오.', '재활용, 쓰레기, 분리수거, 비치', 'EXIST', 1, NOW(), NOW());
INSERT INTO `TB_Chatbot` (ctype, title, content, keywords, status, user_id, created_date, updated_date) VALUES ('FAQ', '[질문] 24시간 이용 가능한 공간이 있나요?', '[답변] 지정된 일부 공간만 24시간 이용 가능하며 사전 등록이 필요합니다.', '24시간, 이용, 공간, 등록', 'EXIST', 1, NOW(), NOW());
INSERT INTO `TB_Chatbot` (ctype, title, content, keywords, status, user_id, created_date, updated_date) VALUES ('FAQ', '[질문] 외부 방문객도 동반 가능한가요?', '[답변] 회원 동반 시 안내 데스크에 방문객 등록 후 이용 가능합니다.', '외부, 방문객, 동반, 등록', 'EXIST', 1, NOW(), NOW());
INSERT INTO `TB_Chatbot` (ctype, title, content, keywords, status, user_id, created_date, updated_date) VALUES ('FAQ', '[질문] 헤드셋을 대여할 수 있나요?', '[답변] 안내 데스크에서 무료로 대여 가능합니다.', '헤드셋, 대여, 무료, 안내 데스크', 'EXIST', 1, NOW(), NOW());
INSERT INTO `TB_Chatbot` (ctype, title, content, keywords, status, user_id, created_date, updated_date) VALUES ('FAQ', '[질문] 빔 프로젝터를 사용할 수 있나요?', '[답변] 회의실에 빔 프로젝터와 스크린이 기본 설치되어 있습니다.', '빔 프로젝터, 회의실, 스크린, 설치', 'EXIST', 1, NOW(), NOW());
INSERT INTO `TB_Chatbot` (ctype, title, content, keywords, status, user_id, created_date, updated_date) VALUES ('FAQ', '[질문] 우편물 수령 대행 서비스가 있나요?', '[답변] 장기 이용 고객에 한하여 우편물 수령 대행 서비스를 제공합니다.', '우편물, 수령 대행, 장기 이용, 서비스', 'EXIST', 1, NOW(), NOW());

-- RULE (10개)
INSERT INTO `TB_Chatbot` (ctype, title, content, keywords, status, user_id, created_date, updated_date) VALUES ('RULE', '소음 규정 준수', '공유 공간에서는 타인의 업무에 방해되지 않도록 소음을 최소화해야 합니다.', '소음, 규정, 준수, 공유 공간, 방해', 'EXIST', 1, NOW(), NOW());
INSERT INTO `TB_Chatbot` (ctype, title, content, keywords, status, user_id, created_date, updated_date) VALUES ('RULE', '음식물 섭취 규정', '냄새가 강한 음식물은 지정된 카페테리아 구역에서만 섭취 가능합니다.', '음식물, 섭취, 규정, 카페테리아, 냄새', 'EXIST', 1, NOW(), NOW());
INSERT INTO `TB_Chatbot` (ctype, title, content, keywords, status, user_id, created_date, updated_date) VALUES ('RULE', '반려동물 출입 제한', '시설 내 모든 구역에서 반려동물 동반은 금지됩니다.', '반려동물, 출입, 제한, 금지', 'EXIST', 1, NOW(), NOW());
INSERT INTO `TB_Chatbot` (ctype, title, content, keywords, status, user_id, created_date, updated_date) VALUES ('RULE', '흡연 금지 구역', '흡연은 건물 외부의 지정된 흡연 구역에서만 가능합니다.', '흡연, 금지, 구역, 건물 외부', 'EXIST', 1, NOW(), NOW());
INSERT INTO `TB_Chatbot` (ctype, title, content, keywords, status, user_id, created_date, updated_date) VALUES ('RULE', '개인 물품 관리', '개인 물품은 본인이 책임지고 관리해야 하며 분실 시 시설은 책임지지 않습니다.', '개인 물품, 관리, 책임, 분실, 책임', 'EXIST', 1, NOW(), NOW());
INSERT INTO `TB_Chatbot` (ctype, title, content, keywords, status, user_id, created_date, updated_date) VALUES ('RULE', '회의실 사용 시간 준수', '회의실 예약 시간 초과 및 무단 사용을 금지합니다.', '회의실, 사용 시간, 준수, 예약, 무단 사용', 'EXIST', 1, NOW(), NOW());
INSERT INTO `TB_Chatbot` (ctype, title, content, keywords, status, user_id, created_date, updated_date) VALUES ('RULE', '공용 장비 반납 원칙', '대여한 장비는 사용 후 반드시 대여 장소에 즉시 반납해야 합니다.', '공용 장비, 대여, 반납, 원칙', 'EXIST', 1, NOW(), NOW());
INSERT INTO `TB_Chatbot` (ctype, title, content, keywords, status, user_id, created_date, updated_date) VALUES ('RULE', '보안 및 출입 통제', '출입카드는 타인에게 양도 또는 공유할 수 없습니다.', '보안, 출입 통제, 출입카드, 양도, 공유', 'EXIST', 1, NOW(), NOW());
INSERT INTO `TB_Chatbot` (ctype, title, content, keywords, status, user_id, created_date, updated_date) VALUES ('RULE', '사진 및 영상 촬영 규제', '시설 내에서 타인의 동의 없이 무단 촬영하는 것을 금지합니다.', '사진, 영상, 촬영, 규제, 무단 촬영, 동의', 'EXIST', 1, NOW(), NOW());
INSERT INTO `TB_Chatbot` (ctype, title, content, keywords, status, user_id, created_date, updated_date) VALUES ('RULE', '시설 청결 유지', '사용 후 발생한 쓰레기는 지정된 분리수거함에 버리고, 자리를 정리해야 합니다.', '청결, 유지, 쓰레기, 분리수거, 정리', 'EXIST', 1, NOW(), NOW());