-- 스케줄 소유자 또는 참가자일때의 리스트 출력
select schedule_no, schedule_title, start_time, end_time, owner, m_owner.name as owner_name,
  ss.participant_no, m_member.name as participant_name
  from (select sch.schedule_no, sch.schedule_title, sch.start_time, sch.end_time, sch.owner,
    sp.member_no as participant_no
    from scheduleapp_schedule as sch inner join scheduleapp_schedule_participants as sp
    on sch.schedule_no=sp.schedule_no where sch.owner=2 or sp.member_no=2) ss
      inner join scheduleapp_member m_owner on ss.owner=m_owner.member_no
      inner join scheduleapp_member m_member on ss.participant_no=m_member.member_no;
    
-- 스케줄의 참가자 명단 출력
select sp.schedule_no, m.member_no, m.name
  from scheduleapp_schedule_participants as sp inner join scheduleapp_member m 
    on sp.member_no=m.member_no
  where schedule_no=1
  order by member_no;
  
  