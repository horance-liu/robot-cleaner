#ifndef H90006F86_7D30_47FB_8581_D302F8F6B7EB
#define H90006F86_7D30_47FB_8581_D302F8F6B7EB

#include <infra/base/Keywords.h>

//////////////////////////////////////////////////////////////////
namespace details
{
    struct Role
    {
        virtual ~Role() = default; 
    };
}

//////////////////////////////////////////////////////////////////
#define DEFINE_ROLE(type) struct type : ::details::Role

//////////////////////////////////////////////////////////////////
#define SELF(self, role) static_cast<role&>(self)
#define SELF_CONST(self, role) static_cast<const role&>(self)

#endif
