#include "git.h"


// �����ʱ���趨
static Timer task1_id;
static Timer task2_id;
static Timer task3_id;
extern u8 time25ms;

// ��ȡȫ�ֱ���
const char *topics[] = {S_TOPIC_NAME};

// Ӳ����ʼ��
void Hardware_Init(void)
{
    NVIC_PriorityGroupConfig(NVIC_PriorityGroup_2); // �����ж����ȼ�����Ϊ��2��2λ��ռ���ȼ���2λ��Ӧ���ȼ�
    HZ = GB16_NUM();                                // ����
    delay_init();                                   // ��ʱ������ʼ��
    GENERAL_TIM_Init(TIM_4, 0, 1);
    Usart1_Init(115200); // ����1��ʼ��Ϊ115200
	 
    while (Reset_Threshole_Value(&threshold_value_init, &device_state_init) != MY_SUCCESSFUL)
        delay_ms(5); // ��ʼ����ֵ
  
#if OLED // OLED�ļ�����
    OLED_Clear();
#endif
}
// �����ʼ��
void Net_Init()
{
		char str[50];
// �豸����
#if NETWORK_CHAEK
    if (Connect_Net == 0) {
			
#if OLED // OLED�ļ�����
        OLED_Clear();
        // дOLED����
        sprintf(str, "-���WIFI�ȵ�");
        OLED_ShowCH(0, 0, (unsigned char *)str);
        sprintf(str, "-����:%s         ", SSID);
        OLED_ShowCH(0, 2, (unsigned char *)str);
        sprintf(str, "-����:%s         ", PASS);
        OLED_ShowCH(0, 4, (unsigned char *)str);
        sprintf(str, "-Ƶ��: 2.4 GHz   ");
        OLED_ShowCH(0, 6, (unsigned char *)str);
#endif
     
    }
#endif
}

// ����1
void task1(void)
{
	
 	Automation_Close();//1�������
	Update_oled_massage();   // ����OLED
}
// ����2
void task2(void)
{
 
													 // BEEP= ~BEEP;
	State = ~State;
}
// ����3
void task3(void)
{
	// ������Ϣ
	if(Data_init.cmd < 2){
		Data_init.cmd ++;
	}else{
		Data_init.cmd = 1;
	}
	// ������Ϣ
  if (Connect_Net && Data_init.App == 0) {
      Data_init.App = Data_init.cmd;
			
  }
		
}
// �����ʼ��
void SoftWare_Init(void)
{
    // ��ʱ����ʼ��
    timer_init(&task1_id, task1, 1000, 1); // 1sִ��һ��
    timer_init(&task2_id, task2, 300, 1);  // 300msִ��һ��
    timer_init(&task3_id, task3, 4000, 1); // 4sִ��һ��
 
}
// ������
int main(void)
{

    unsigned char *dataPtr = NULL;
    SoftWare_Init(); // �����ʼ��
    Hardware_Init(); // Ӳ����ʼ��
    Net_Init(); // �����ʼ
    TIM_Cmd(TIM4, ENABLE); // ʹ�ܼ�����

    while (1) {

        // �߳�
        timer_loop(); // ��ʱ��ִ��
        // ���ڽ����ж�
        dataPtr = ESP8266_GetIPD(0);
     
    }
}

