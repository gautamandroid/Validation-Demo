package com.gautam.validatonformgrewon.utilslist

import android.content.Context
import android.media.Image
import com.gautam.validatonformgrewon.R
import com.gautam.validatonformgrewon.apimodal.CategoriesResponse
import com.gautam.validatonformgrewon.apimodal.HomeResponse
import com.gautam.validatonformgrewon.modal.*

class UtilList {

    companion object {

        fun getItemList(context: Context): ArrayList<CategoriesResponse> {
            val maniyitemlist = ArrayList<CategoriesResponse>()
            /*maniyitemlist.add(
                IteamList(
                    1,
                    context.getString(R.string.sports),
                    context.getString(R.string.sportimage)
                )
            )
            maniyitemlist.add(
                IteamList(
                    2,
                    context.getString(R.string.Bikes),
                    context.getString(R.string.bikesimage)
                )
            )
            maniyitemlist.add(
                IteamList(
                    3,
                    context.getString(R.string.mobiles),
                    context.getString(R.string.mobileimage)
                )
            )
            maniyitemlist.add(
                IteamList(
                    4,
                    context.getString(R.string.shirt),
                    context.getString(R.string.sharetsimage)
                )
            )
            maniyitemlist.add(
                IteamList(
                    5,
                    context.getString(R.string.t_shirt),
                    context.getString(R.string.t_shiretimage)
                )
            )
            maniyitemlist.add(
                IteamList(
                    6,
                    context.getString(R.string.laptop),
                    context.getString(R.string.laptopsimage)
                )
            )
            maniyitemlist.add(
                IteamList(
                    7,
                    context.getString(R.string.jewellery),
                    context.getString(R.string.jewllriimage)
                )
            )*/
            return maniyitemlist
        }

        fun getshoppingList(context: Context): ArrayList<Shopping> {
            var shopping = java.util.ArrayList<Shopping>()

            shopping.add(
                Shopping(
                    2,
                    context.getString(R.string.lunchboximage),
                    context.getString(R.string.lunch_Box),
                    4.5
                )
            )
            shopping.add(
                Shopping(
                    3,
                    context.getString(R.string.lightimage),
                    context.getString(R.string.LightLamp),
                    4.0
                )
            )
            shopping.add(
                Shopping(
                    4,

                    context.getString(R.string.Facialimage),
                    context.getString(R.string.Facial_wash),
                    3.5
                )
            )
            shopping.add(
                Shopping(
                    5,
                    context.getString(R.string.Acimage),
                    context.getString(R.string.AC),
                    5.0
                )
            )
            shopping.add(
                Shopping(
                    1,
                    context.getString(R.string.bottleimage),
                    context.getString(R.string.bottle),
                    2.5

                )
            )

            return shopping
        }

        fun getAlliteamList(context: Context): ArrayList<HomeResponse> {
            var list = ArrayList<HomeResponse>()

        /*    list.add(
                Listview(
                    1,
                    context.getString(R.string.helthyharimage),
                    3.5,
                    "Prevents dandruff, itchy scalp, and keeps hair healthy by improving circulation.",
                    context.getString(R.string.healthy_hair)

                )
            )
            list.add(
                Listview(
                    2,
                    context.getString(R.string.Zanduimage),
                    4.5,
                    "Zandu Kesari Jivan gives you power & helps build endurance while keeping you energetic, throughout the day.",
                    context.getString(R.string.Zandu)
                )
            )
            list.add(
                Listview(
                    3,
                    context.getString(R.string.homepreimium_image),
                    4.0,
                    "Each Lama is unisex & size-inclusive which means we are one size fits all",
                    context.getString(R.string.Home_premium_store)
                )
            )
            list.add(
                Listview(
                    4,
                    context.getString(R.string.Symactive_image),
                    5.0,
                    "Warranty: This product comes with a 6 months warranty.",
                    context.getString(R.string.Symactive)
                )
            )
            list.add(
                Listview(
                    5,
                    context.getString(R.string.Manjauar_image),
                    4.5,
                    "Kurta Material :- Cotton Blend, Bottom Material: Cotton",
                    context.getString(R.string.Manjauar)
                )
            )*/
            return list
        }

        fun getMenuList(context: Context): ArrayList<ListRecyclerBurgurKing> {
            var manulist = ArrayList<ListRecyclerBurgurKing>()
            manulist.add(
                ListRecyclerBurgurKing(
                    1,
                    context.getString(R.string.boss_image),
                    4.5,
                    "Foodies of all ages enjoy and appreciate Fast Food",
                    context.getString(R.string.boss_whopper_veg),
                    context.getString(R.string._149)
                )
            )
            manulist.add(
                ListRecyclerBurgurKing(
                    2,
                    context.getString(R.string.VegImage),
                    3.0,
                    "There are a number of alternatives, ranging from quaint restaurants to upscale dining chains",
                    context.getString(R.string.VEG_roll),
                    context.getString(R.string._199)
                )
            )
            manulist.add(
                ListRecyclerBurgurKing(
                    3,
                    context.getString(R.string.TikkaImage),
                    4.0,
                    "if you have any specific requirements, you need to talk to the food joint",
                    context.getString(R.string.TIKKA_burgurr),
                    context.getString(R.string._250)
                )
            )
            manulist.add(
                ListRecyclerBurgurKing(
                    4,
                    context.getString(R.string.ice_image),
                    2.5,
                    "A little ice cream and a cold drink was a welcome sight on a warm day in Willemstad, Curacao!",
                    context.getString(R.string.ice_color),
                    context.getString(R.string._99)
                )
            )
            manulist.add(
                ListRecyclerBurgurKing(
                    5,
                    context.getString(R.string.pri_periImage),
                    5.0,
                    "The classic burger is an all time BBQ favourite! This super easy homemade beef burger recipe gives you delicious patties,",
                    context.getString(R.string.prei_peri),
                    context.getString(R.string._149),
                            tyepimage = "image"
                )
            )
            manulist.add(
                ListRecyclerBurgurKing(
                    6,
                    context.getString(R.string.French_Image),
                    3.5,
                    "Ademar de Barros Moreira owns the legendary french fry stand Batata de Marcehal in Rio de Janeiro.",
                    context.getString(R.string.French_fries),
                    context.getString(R.string._199)

                )
            )
            manulist.add(
                ListRecyclerBurgurKing(
                    6,
                    context.getString(R.string.French_Image),
                    3.5,
                    "Ademar de Barros Moreira owns the legendary french fry stand Batata de Marcehal in Rio de Janeiro.",
                    context.getString(R.string.French_fries),
                    context.getString(R.string._199)

                )
            )
            manulist.add(
                ListRecyclerBurgurKing(
                    6,
                    context.getString(R.string.French_Image),
                    3.5,
                    "Ademar de Barros Moreira owns the legendary french fry stand Batata de Marcehal in Rio de Janeiro.",
                    context.getString(R.string.French_fries),
                    context.getString(R.string._199)

                )
            )
            manulist.add(
                ListRecyclerBurgurKing(
                    6,
                    context.getString(R.string.French_Image),
                    3.5,
                    "Ademar de Barros Moreira owns the legendary french fry stand Batata de Marcehal in Rio de Janeiro.",
                    context.getString(R.string.French_fries),
                    context.getString(R.string._199)
                , tyepimage = "image"

                )
            )
            manulist.add(
                ListRecyclerBurgurKing(
                    6,
                    context.getString(R.string.French_Image),
                    3.5,
                    "Ademar de Barros Moreira owns the legendary french fry stand Batata de Marcehal in Rio de Janeiro.",
                    context.getString(R.string.French_fries),
                    context.getString(R.string._199)

                )
            )
            manulist.add(
                ListRecyclerBurgurKing(
                    6,
                    context.getString(R.string.French_Image),
                    3.5,
                    "Ademar de Barros Moreira owns the legendary french fry stand Batata de Marcehal in Rio de Janeiro.",
                    context.getString(R.string.French_fries),
                    context.getString(R.string._199)

                )
            )
            manulist.add(
                ListRecyclerBurgurKing(
                    6,
                    context.getString(R.string.French_Image),
                    3.5,
                    "Ademar de Barros Moreira owns the legendary french fry stand Batata de Marcehal in Rio de Janeiro.",
                    context.getString(R.string.French_fries),
                    context.getString(R.string._199)

                )
            )
            manulist.add(
                ListRecyclerBurgurKing(
                    6,
                    context.getString(R.string.French_Image),
                    3.5,
                    "Ademar de Barros Moreira owns the legendary french fry stand Batata de Marcehal in Rio de Janeiro.",
                    context.getString(R.string.French_fries),
                    context.getString(R.string._199)

                )
            )
            manulist.add(
                ListRecyclerBurgurKing(
                    6,
                    context.getString(R.string.French_Image),
                    3.5,
                    "Ademar de Barros Moreira owns the legendary french fry stand Batata de Marcehal in Rio de Janeiro.",
                    context.getString(R.string.French_fries),
                    context.getString(R.string._199)

                )
            )
            manulist.add(
                ListRecyclerBurgurKing(
                    6,
                    context.getString(R.string.French_Image),
                    3.5,
                    "Ademar de Barros Moreira owns the legendary french fry stand Batata de Marcehal in Rio de Janeiro.",
                    context.getString(R.string.French_fries),
                    context.getString(R.string._199), tyepimage = "image"

                )
            )

            return manulist
        }


    }
}
