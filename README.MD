# SMGP TermProject

---------
+ 2016184004 김용진
---------

---------
게임 간단한 소개 
---------
![Image01](https://imgur.com/67nSN7A.png)
+ 게임 : 드래곤 플라이트 모작
+ 장르 : 2D 슈팅 게임
+ 컨셉 : 일반 몬스터와 보스 몬스터의 공격을 피해가면서 무찌르자!

---------			
현재까지의 진행 상황
---------
![Image02](https://imgur.com/flEUfMi.png)

아이템 기능과 2개의 보스 몬스터 중 1종을 우선적으로 제작해야 하는 상황입니다.

---------
Git Commit 
---------
![Image03](https://imgur.com/j4bPR6y.png)

총 Commit 횟수는 30회 입니다.

---------
MainGame에 등장하는 game object - (4개의 Class 추가 - Boss, BossBullet, Heart, Particle)
---------

- Boss, BossBullet, EnemyGenerator 상호작용 

![Image04](https://imgur.com/bM3kWWB.png)

(1) EnemyGenerator 클래스에서 wave가 20이 되는 시점에 Boss 객체를 생성한다.

(2) Boss 클래스의 fireBullet() 함수에서 BossBullet 객체를 4개 생성한다.
    BossBullet은 가로로 5등분 했을 때 랜덤하게 4개의 구간에서 총알이 발사된다.


- Heart와 Particle

![Image05](https://imgur.com/OboCGfd.png)

(1) MainGame 클래스의 initResources() 함수에서 3개의 하트를 추가시킨다.
(2) Particle 은 일정한 시간 이후 사라져야 하므로 Particle 클래스의 update() 에서 시간을 재서 지우도록 했다.


- 충돌처리

![Image06](https://imgur.com/wEJNGul.png)

(1) 보스 몬스터의 총알 --> 플레이어 충돌 시점 :  

HitPlayerCount 변수를 두어 몇 번을 맞았는지 Check

Heart 삭제

Particle 객체 생성

(2) 플레이어 총알 --> 보스 몬스터 충돌 시점 : 

HitbossCount 변수를 두어 몇 번을 맞았는지 Check

현재는 플레이어 총알 100발을 맞는 시점에 remove(boss)를 하면서 스코어를 100점 올리도록 했다. 


