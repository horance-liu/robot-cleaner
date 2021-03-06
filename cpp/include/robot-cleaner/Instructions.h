#ifndef HC37C3D94_43F1_4677_BD56_34CB78EFEC75
#define HC37C3D94_43F1_4677_BD56_34CB78EFEC75

#include "infra/base/Role.h"
#include <initializer_list>

struct Point;
struct Orientation;

DEFINE_ROLE(Instruction)
{
    ABSTRACT(void exec(Point& point, Orientation& orientation) const);
};

Instruction* left();
Instruction* right();

Instruction* forward_n(int n);
Instruction* backward_n(int n);
Instruction* repeat(Instruction*, int n);

inline Instruction* forward()
{ return forward_n(1);  }

inline Instruction* backward()
{ return backward_n(1); }

inline Instruction* round()
{ return repeat(right(), 2); }

Instruction* __sequential(std::initializer_list<Instruction*>);

#define sequential(...) __sequential({ __VA_ARGS__ })

#endif
