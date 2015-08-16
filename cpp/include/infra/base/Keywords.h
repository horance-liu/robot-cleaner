#ifndef HA569F0D0_5A46_4EAE_AE57_2500191C1EFF
#define HA569F0D0_5A46_4EAE_AE57_2500191C1EFF

#define ABSTRACT(...) virtual __VA_ARGS__ = 0
#define OVERRIDE(...) virtual __VA_ARGS__ override
#define EXTENDS(...) , ##__VA_ARGS__
#define IMPLEMENTS(...) EXTENDS(__VA_ARGS__)

#endif
