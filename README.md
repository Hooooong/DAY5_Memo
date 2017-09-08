JAVA Programing
----------------------------------------------------
#### 2017.09.08 5일차

###### 예제
____________________________________________________

  1. I/O를 통한 Memo 작성
  2. DB를 통한 Memo 작성

###### 공부내용
____________________________________________________
  - 예외(Exception)

    - 예외(Exception)란?
      > JAVA에서 일종의 오류로 일반 예외, 실행 예외로 나눌 수 있다.<br>일반 예외는 컴파일시 발생할 수 있는 오류, 실행 예외는 프로그램 실행시 상황에 발생하는 오류이다.<br>일반 예외가 발생할 수 있는 메소드를 사용할 경우 반드시 예외를 처리하는 코드를 함께 작성해야한다. 하지만 실행 예외는 주로 프로그램의 오류가 있을 때 발생하기 때문에 예외 처리를 선언하지 않아도 된다.(개발자의 실수)

    - 예외 처리

      - JAVA는 프로그램 실행중에 발생할 수 있는 예외 처리문을 제공한다.
      - JAVA의 예외 처리는 예외가 발생한 메서드 내에서 직접 처리하는 방법과 예외가 발생한 메소드를 호출한 곳으로 예외 객체를 넘겨주는 방법이 있다.

      1. 예외 발생 시 직접 처리 (try-catch-finally)

            ```java
            try{

              // 예외 발생 예상 구역

            }catch(예외명1 | 예외명2 e){
              // 예외처리
              // JDK 1.7 부터 예외명를 두개 이상 사용할 수 있다. (구분자 '|')
              // 예외가 같은 클래스에 속하는 경우에만 사용할 수 있다.
            }catch(예외명3 e){
              // 예외처리
            }finally{

              //try와 catch 가 끝나면 무조건 실행되는 구역

            }
            ```

            - 기본 구문은 try-catch-finally 로 작성할 수 있다. 물론, finally 는 생략 가능하다.
            - finally는 예외가 발생하던, 발생하지 않던 무조건 실행한다.

      2. 예외 던저주기(throw, throws)

            ```java
            public class SampleException{
              public static void main(String[] args){
                try{
                  // 메소드를 사용하는 곳에서 Exception 예외를 처리하는 구문을 작성한다.
                  testException();
                }catch(Exception e){
                  // 오류 처리
                }

              }
              // 직접 예외를 처리하지 않고  Exception의 예외를 던저준다.
              public void testException() throws Exception{
                // 오류 발생 구역
                // throw 를 사용하면 예외를 발생시킬 수 있다.
                throw new Exception();
              }
            }
            ```

            - throw new 예외이름(); 을 사용하면 강제로 예외를 발생시킨다.
            - class 나 메소드 옆에 thrwos 예외이름 을 작성하면 예외가 발생하더라도 사용한 곳에서 처리하게 예외를 던저준다.

      3. 추가된 try-with-resource

        ```java
        // try()에 자원을 사용하는 객체들을 넣어 생성한다.
        // 1개 이상의 객체들을 넣을 수 있다.
        try(FileInputStream fis = new FileInputStream();
          Connection con = DriverManager.getConnection();){

        }catch(Exception e){

        }
        ```

        - JDK 1.7 부터 try-with-resource 문이 추가되어
        - try {자원 객체;} 를 넣으면 자동으로 자원의 close()를 해준다.
  		  - 하지만 자원을 사용하는 모든 객체들을 넣을 수 있는게 아니고 AutoCloseable을 implements한 객체만 넣을 수 있다.

  - I/O Stream

      - I/O(Input/Output, 입출력) 란?

          > 하나의 시스템에서 다른 시스템으로 데이터를 이동하는 일을 입출력(I/O)이라고 한다.
          <br> IO는 Stream 기반으로 입력 Stream과 출력 Stream으로 구분되어 있기 때문에 데이터를 읽기 위해서는 입력 Stream을 생성해야 하고, 데이터를 출력하기 위해서는 출력 Stream을 생성해야 한다.

      - I/O class

          - 명칭의 종류

              - Strem 으로 시작하는 클래스 : Byte 단위로 입출력을 수행하는 클래스

              - Reader / Writer 로 끝나는 클래스 : 문자 단위로 입출력을 수행하는 클래스

              - File 로 시작하는 클래스 : HDD의 File 을 사용하는 클래스

              - Buffered 로 시작하는 클래스 : 시스템의 버퍼를 사용하는 클래스

          - FileOutputStream, OutputStreamWirter, BufferedWirter

              - FileOutputStream : Byte Stream 을 Btye 파일로 변환
              - OutputStreamWirter : 문자 Stream 을 Byte Stream 으로 변환
              - BufferedWirter : 문자 Stream 에 버퍼 출력, 줄바꿈 사용

              ```java
              /*
               * FileOutputStream (Byte단위 출력하는 Stream)
               *  			|
               * OutputStreamWriter (byte단위를 문자 단위 로 변환해주는 출력 Stream )
               *  			|
               * BufferedWriter (문자 단위를 출력하는 Stream (보조 Stream))
               *
               */
              // 1. 출력하는 스트림을 연다.
              FileOutputStream fos = new FileOutputStream(database, true);
              // 2. 스트림을 중간 처리한다. (Text 의 Encoding 을 변경하는 작업)
              OutputStreamWriter osw = new OutputStreamWriter(fos, "인코딩 명");
              // 3. 버퍼 처리
              BufferedWriter bw = new BufferedWriter(osw);
              ```

          - FileInputStream, InputStreamReader, BufferedReader

              - FileInputStream : 파일에서 Byte 를 읽어 Byte Stream으로 변환
              - InputStreamReader :  Byte Stream 을 문자 Stream 으로 변환
              - BufferedReader : 문자 버퍼 입력, 라인 해석

              ```java
              // 1. 읽는 스트림을 연다
              FileInputStream fis = new FileInputStream(database);
              // 2. 스트림을 중간 처리한다. (Text 의 Encoding 을 변경하는 작업)
              InputStreamReader isr = new InputStreamReader(fis, "인코딩 명");
              // 3. 버퍼처리
              BufferedReader br = new BufferedReader(isr);
              ```

          - 그 외 클래스

              - File : 파일 객체를 생성한다.

                ```java
                // Database 경로
                private final String DB_DIR = "c:\\workspace\\java\\Memo\\database";
                // File 경로
                private final String DB_FILE = "memo.txt";
                // 구분자
                private final String COLUM_SEP = "::";

                File dir = new File(DB_DIR);

                if(!dir.exists()) {
                  //디렉토리가 없으면

                  // dir.mkdirs();
                  // dir.mkdir();

                  /*
                   * file객체.mkdirs();
                   * file객체.mkdir();
                   *
                   * mkdir() 는 경로가 없으면 error 가 발생하지만
                   * mkdirs() 는 경로가 없으면 그 경로를 생성해 준다.
                   */
                   dir.mkdirs();
                }

                /*
                 * File.separator 를 쓰는 이유
                 *
                 * OS 마다 separator 가 다르다
                 * windows = \
                 * mac, Linux, Unix = /
                 */
                File file = new File(DB_DIR + File.separator + DB_FILE);

                if(!file.exists()) {
                  try {
                    file.createNewFile();
                  } catch (IOException e) {
                    e.printStackTrace();
                  }
                }

                ```
