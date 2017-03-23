[ Date : 2017. 02. 07 ]



					---------------- Today's Topic -----------------
								(1) 구글맵 사용하기
								(2) GPS 사용하기
								(3) DDMS
								(4) ADB shell 사용하기
					------------------------------------------------


프로젝트명 : MyUtility


week5-1.md에서 만든 탭과 프래그먼트의 연결 작업을 응용하여 각 탭마다 지금까지 만든 기능들을 종합하였습니다. [계산기] / [단위변환] / [검색] / [현재위치] 입니다. 이 중에서 새로 배운 '구글맵 사용하기'를 소개하겠습니다!!

-------------------------------------------

#1. 구글맵 사용하기

[구글맵 사용하기]는 자신의 위치를 GPS를 사용하여 실시간으로 구글맵에 표시합니다.


![](http://i.imgur.com/2MBbhlH.png)

##1.1 구글맵과 GPS를 같이 사용하기 위해서는 먼저 manefest에 권한 설정을 해주어야 합니다.

![](http://i.imgur.com/hHiObjW.png)

**android.permission.INTERNET** - API가 Google Maps 서버에서 지도 타일을 다운로드할 때 사용합니다.

**android.permission.ACCESS_FINE_LOCATION** - 애플리케이션이 device의 위치를 실시간으로 접근하도록 허락합니다. (정확한 위치 정보)

**android.permission.ACCESS_COARSE_LOCATION** - 애플리케이션이 device의 위치를 실시간으로 접근하도록 허락합니다. (근사 위치 정보)


##1.2. 구글 맵 프로젝트 생성과 구글 API 키 가져오기

Google Maps Android API를 사용하려면 Google Developers Console에 앱을 등록하고 추가할 수 있는 API 키가 필요합니다.

###1.2.1
먼저 "Google Maps Activity"를 생성합니다.

![](http://i.imgur.com/PyyQbwG.png)

###1.2.2

 OnMapReadyCallback 인터페이스를 FourFragment에서 구현합니다.

			public class FourFragment extends Fragment implements OnMapReadyCallback {
			   ...............................
			}



###1.2.3

![](http://i.imgur.com/8QYtBPl.png)

(1) app/res/values/google_maps_api.xml로 이동합니다.

(2) 표시된 링크로 가면 Google Developers Console로 이동합니다. Console 화면에 나온대로 따라하면 구글 키 생성!

(3) 구글 key를 string 태그 안에 넣으면 됩니다. 

##1.3 Mapview 추가 

MyUtility 프로젝트에서 구글맵이 표시될 화면은 '현재위치' 탭 아래 화면입니다. 이 화면에 해당하는 "fragment_four.xml"에 mapview 프래그먼트를 추가하고 ID를 'mapView'로 합니다.

##1.4 Mapview 프래그먼트를 액티비티에 추가.
우리가 사용하는 액티비티에는 뷰페이저가 등록이 되어있습니다. 뷰페이저에 어뎁터를 setting시킴으로 화면에 표시가 되는데 이 때, 우리가 사용할 어뎁터로 fragmentStatePageAdapter를 상속하는 어뎁터를 사용할 겁니다. 

결론적으로 Mapview를 화면에 보여주기 위해 어디에 등록해야 하냐? 

----> Mapview를 프래그먼트에 등록시킨 후, 프래그먼트를 어뎁터 클래스에서 객체로 생성하여 반환해주면 됩니다.(MainActivity.java) 여기서는 프래그먼트의 onCreateView(프래그먼트가 화면에 보여질 때)에 아래 과정을 넣어주면 됩니다.(FourFragment.java)

![](http://i.imgur.com/lg3fo1W.png)

##1.5 OnMapReadyCallback 인터페이스 메소드 정의하
	

![](http://i.imgur.com/CrIWioO.png)


1.2.2에서 OnMapReadyCallback 인터페이스를 implements하면 onMapReady 콜백 메소드를 정의해주어야합니다. 여기서는 GoogleMap 객체(map)를 사용하여 가령 지도에 시드니 도시의 마커를 표시(.addMarker)하고 화면을 시드니가 있는 쪽으로 옮깁니다.(.moveCamer) 이때 zoom level은 15 정도만 줬습니다. 


> 여기까지가 구글맵을 띄우는 과정이었습니다!!! 휴.........
> 2장부터는 GPS를 사용하여 내 위치를 실시간으로 마크해주는 기능을 넣겠습니다

------------------------------------------------

#2. GPS 사용하기

				          	<GPS를 사용하는 법>
					 * 1. manefest에 FINE, COARSE 권한추가
					 * 2. runtime permission 코드에 추가
					 * 3. GPS Location Manager 정의
					 * 4. GPS가 켜져있는지 확인. 꺼져있다면 GPS화면으로 이동
					 * 5. Listener 실행
					 * 6. Listener 해제




![](http://i.imgur.com/yPDGFTB.png)

전체적인 코드 진행은 위와 같습니다.

##2.1 manefest에 FINE, COARSE 권한추가

1번은 1.1에서 설정했으므로 생략합니다^^

##2.2 runtime permission 코드에 추가

![](http://i.imgur.com/LMy7B0R.png)

GPS를 사용하기 위해서는 사용자에게 위치 탐색 권한을 요청해야 합니다. 
따라서 "Manifest.permission.ACCESS_FINE_LOCATION"와 "Manifest.permission.ACCESS_COARSE_LOCATION"를 반드시 권한체크 문과 권한체크 후 콜백문에 넣어주어야 합니다!!



##2.3 GPS Location Manager 정의

Location Manager는 실시간으로 device의 위치 정보를 제공해주는 역할입니다. Location Manager를 불러올 때는 **Context.getSystemService(Context.LOCATION_SERVICE)** 를 사용하면 됩니다.

		LocationManager manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

##2.4 GPS가 켜져있는지 확인. 꺼져있다면 GPS화면으로 이동

[MainActivity.java]
![](http://i.imgur.com/QnyAjEc.png)

GPS가 켜져있는지 여부를 팝업으로 사용자에게 알리는 코드입니다.

(1) 직접 만든 gpsCheck()로 GPS가 켜져있는지를 확인합니다.

(2) 안켜져 있다면, AlertDialog.Builder 객체를 선언합니다.

>> AlertDialog.Builder은 사용자 인터페이스에서 필요한 대부분의 대화 상자를 구현하게 합니다.

(3) alertDialog.setTitle() : 팝업창 제목을 만듭니다.

(4) alertDialog.setMessage() : 팝업창 메시지를 만듭니다. 

(5) alertDialog.setPositiveButton : Yes 버튼을 눌렀을 때. intent에 Settings.ACTION_LOCATION_SOURCE_SETTINGS을 담아 시스템에 전달합니다. GPS 설정 화면으로 넘어갑니다. 

(6)  alertDialog.setNegativeButton : No 버튼을 눌렀을 때. 이 때는 dialog.cancel()로 대화상자를 그냥 닫습니다.

(7) alertDialog.show() : 팝업창을 화면에 띄웁니다.


[MainActivity.java]
![](http://i.imgur.com/4Opte66.png)

위 코드는 롤리팝 이하 버전일 경우 gps가 켜져있는지를 시스템 내부에서 확인하는 과정입니다.

### GPS 설정 화면

![](http://i.imgur.com/pwzyY26.png)


##2.5 Listener 실행

GPS를 사용하기 위해서는 위치좌표 값을 불러와야 합니다. 위치 값을 매번 얻어올 때, 필요한 것이 리스너(LocationListener) 등록입니다. 이 리스너는 모바일 화면에서 일정 거리 기준으로 움직임이 발생할 때마다 호출됩니다. 

###2.5.1 리스너 등록

[FourFragment.java]
![](http://i.imgur.com/b8j5DUG.png)

(1) 먼저 device의 OS 버전이 마시멜로 이상인지를 체크하고 ACCESS_COARSE_LOCATION와 ACCESS_FINE_LOCATION 퍼미션이 권한 체크 되 있는지 확인합니다.

(2) requestLocationUpdates() -- GPS_PROVIDER.
2.3에서 선언한 manager를 통하여 GPS 제공자의 정보가 바뀔때마다 콜백하도록 합니다.

(3) requestLocationUpdates() -- NETWORK_PROVIDER.
2.3에서 선언한 manager를 통하여 네트워크 제공자의 정보가 바뀔때마다 콜백하도록 합니다.

>> 위치 제공자는 GPS_PROVIDER, NETWORK_PROVIDER 2가지 종류가 있습니다. 실내에서는 GPS_PROVIDER를 호출해도 응답이 없습니다. 따라서 타이머를 설정하여 GPS_PROVIDER를 호출 한 뒤 일정 시간이 지나도 응답이 없을 경우 NETWORK_PROVIDER를 호출 하거나, 또는 둘 다 한꺼번에 호출하여 들어오는 값을 사용하는 방식이 일반적입니다. 저희 코드에서는 둘다를 호출하도록 하였습니다.

( 출처 : [http://biig.tistory.com/74](http://biig.tistory.com/74) )

###2.5.2 리스너 구현

[FourFragment.java]
![](http://i.imgur.com/qtsxC7L.png)

여기서는 GPS로 디바이스의 위치값이 변경될 때마다 이벤트가 발생하였을 때를 처리하기 위한 코드입니다. 위치값은 Location 형태로 오기 때문에 현재 위도, 경도, 고도 등의 값들은 Location 안에 정의되어 있는 함수들을 사용하면 됩니다. 

이렇게 자신의 위치값을 가져온 후에는, map.addmarker()로 구글맵에 마크표시를 하고난 뒤 map.moveCamera로 화면을 내 위치로 이동시키면 끝~!!



##2.6 Listener 해제

![](http://i.imgur.com/Q23YMPW.png)

더이상 위치값을 호출하지 않아도 되는 경우에는 removeUpdates()로 자원해제를 반드시 해 줍니다. 여기서는 프래그먼트가 유저와의 상호작용이 끝나는 단계인 onPause()에서 정의하였습니다.


---------------------------------------------------------


#3.  DDMS


DDMS(Dalvik Debug Monitoring Service)는 에뮬레이터/단말기 내의 로그나 실행중인 프로세스 확인이나 화면 캡쳐 등의 작업을 할 수 있습니다

![](http://i.imgur.com/DfJu89x.png)

[Tools] -> [Android] -> [Android Device Monitor]로 들어가면 아래와 같은 화면이 나옵니다.

![](http://i.imgur.com/RVTeXeM.png)

왼쪽에 "Devices" 에는 자신이 사용한 에뮬레이터나 핸드폰의 목록이 나타납니다.





           여러가지 기능들이 있지만, 자주 사용하는 기능은 아래 다섯 가지 정도입니다.
 
					- 프로세스 관리 
					- 에뮬레이터 조작 (Emulator Control)
					- 로그 관리 (LogCat)
					- 파일 관리 (File Explorer)
					- 화면 캡쳐

자세한 사용은 아래 링크를 따라서 "커니의 안드로이드 이야기"에 보시면 자세히 알 수 있습니다.

( 출처 : [http://androidhuman.com/324](http://androidhuman.com/324) )


>> 개인적인 생각으로 로그관리와 파일관리가 개발 과정에서 가장 도움이 될 거 같은데 아직 사용법이 익숙치 않아 잘 모르겠네요;;


#4. ADB shell 사용하기

##4.1 안드로이드의 성능을 최적화하기 위해서 먼저 체크해야 되는 부분이 Render 부분입니다.

안드로이드 기기의 CPU는 그려야 할 것들을 GPU로 보내 GPU에게 이러이러한 것들을 그리라고 명령한다.

GPU는 drawable에 있는 파일들을 래스터화 시켜 그림을 그린다. (벡터 이미지를 비트맵으로 바꾸는것을 래스터라고 한다.)

이 때 발생할 수 있는 문제가 Overdraw 입니다.

##4.2 overdraw

GPU가 그림을 그릴때 과도하게 자원을 낭비하는 것을 말합니다. 주로 과도하게 중첩된 레이아웃에서 발생합니다. 특히 레이아웃 백그라운드에 배경색을 준다든지 하면 해당 레이아웃들을 모두 daw처리해야 되기때문에 부하가 많이 걸리게된다

##4.3 overdraw줄이기

traceview 툴을 이용하여 그래픽컬하게 분석할 수 있습니다.

##4.5 Thread Tracing

Tracing은 애플리케이션이 실행되는 동안 어떤 쓰레드의 어떤 메소드가 얼마나 오래 수행되었는지 알 수 있습니다. 

###4.5.1코드에 methodTrace추가

[MainActivity.java]
![](http://i.imgur.com/XTJWRft.png)

activity의 onCreate() 부분에 startMethodTracing(파일명), stopMethodTracing(파일명)을 넣어 Tracing을 시작합니다.

Tracing이 마치면 /sdcard/파일명.trace 파일이 생성됩니다.

##4.6 ADB Shell에서 생성하기

###4.6.1 adb 명령어가 있는 디렉토리를 환경설정path에 추가합니다.
-  안드로이드 sdk설치폴더 / platform-tools
- 안드로이드 sdk설치폴더/ tools

![](http://i.imgur.com/Z01pOmb.png)

###4.6.2 애뮬레이터에 있는 파일 가져오기
 
C:\Users\JINWOO\AppData\Local\Android\sdk\tools 디렉토리에서 **adb -s 애뮬레이터이름 pull sdcard/파일명.trace 파일명.trace**를 입력합니다.
( 애뮬레이터 목록보기 : adb devices)

###4.6.3. .trace파일보기

C:\Users\JINWOO\AppData\Local\Android\sdk\platform-tools 디렉토리에서 **traceview 파일명.trace** 를 실행하면 아래 화면이 나옵니다.

![](http://i.imgur.com/jaq37e8.png)


##### #Timeline 패널 
timeline 패널은 각 쓰레드별로 메소드가 언제 시작/종료됐는지 색상으로 표시합니다. 주로 main 쓰레드에서 대부분 일이 처리되고 있음을 알 수 있습니다.(마우스를 드래그하여 구간을 선택하면 확대됩니다)

#### #Profile 패널 
profile 패널은 메소드 내부 상세 수행시간을 표시합니다.

자세한 사용법은 아래 출처 링크를 보시면 됩니다.~!!

( 출처 : [http://ecogeo.tistory.com/285](http://ecogeo.tistory.com/285)  )