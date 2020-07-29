/*
 * Copyright 2020 Ankit Ghate
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.ankitgh.employeeportal.utils

import com.ankitgh.employeeportal.ui.addressbook.ContactItem

// fun getPlaceHolderListOfNews() = arrayListOf(
//    NewsArticleModel("Lorem ipsum dolor sit amet, consectetur adipiscing elit.", "2nd June 2020"),
//    NewsArticleModel("Lorem ipsum dolor sit amet, consectetur adipiscing elit.", "3nd June 2020"),
//    NewsArticleModel("Lorem ipsum dolor sit amet, consectetur adipiscing elit.", "4nd June 2020"),
//    NewsArticleModel("Lorem ipsum dolor sit amet, consectetur adipiscing elit.", "5nd June 2020"),
//    NewsArticleModel("Lorem ipsum dolor sit amet, consectetur adipiscing elit.", "6nd June 2020"),
//    NewsArticleModel("Lorem ipsum dolor sit amet, consectetur adipiscing elit.", "7nd June 2020")
//
// )

fun getPlaceHolderListOfContacts() = arrayListOf(
    ContactItem("", "Ankit Ghate", "Sr.Software Developer"),
    ContactItem("", "James Bond", "Sr.Software Developer"),
    ContactItem("", "Roger Dais", "Sr.Software Developer"),
    ContactItem("", "Steven Smith", "Sr.Software Developer"),
    ContactItem("", "Jonty Rodes", "Sr.Software Developer"),
    ContactItem("", "Daniel Craig", "Sr.Software Developer"),
    ContactItem("", "Satya Paul", "Sr.Software Developer"),
    ContactItem("", "Deniss Richie", "Sr.Software Developer"),
    ContactItem("", "Drake Remore", "Sr.Software Developer"),
    ContactItem("", "Dr Dre", "Sr.Software Developer"),
    ContactItem("", "Snoop Dogg", "Sr.Software Developer"),
    ContactItem("", "Mountain Lion", "Sr.Software Developer"),
    ContactItem("", "Sundar Pichai", "Sr.Software Developer"),
    ContactItem("", "Recker", "Sr.Software Developer"),
    ContactItem("", "Vinayak", "Sr.Software Developer"),
    ContactItem("", "Shraddha", "Sr.Software Developer"),
    ContactItem("", "Bhushan", "Sr.Software Developer"),
    ContactItem("", "Pranali", "Sr.Software Developer")
)

// fun getPlaceHolderListofPosts() = arrayListOf(
//
//    FeedPostModel(
//        "www.google.com",
//        "Ankit Ghate",
//        "Senior Developer",
//        "10 min ago",
//        "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Quisque mollis metus ut ornare ultrices. Quisque fringilla hendrerit ipsum pellentesque viverra. Quisque tincidunt felis vitae turpis accumsan dapibus. Aliquam efficitur pulvinar leo, sit amet ornare elit faucibus quis. Pellentesque et magna ornare, tincidunt ante ac"
//    ),
//
//    FeedPostModel(
//        "www.google.com",
//        "Vinayak Hattarki",
//        "Senior System Analyst",
//        "10 min ago",
//        "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Quisque mollis metus ut ornare ultrices. Quisque fringilla hendrerit ipsum pellentesque viverra. Quisque tincidunt felis vitae turpis accumsan dapibus. Aliquam efficitur pulvinar leo, sit amet ornare elit faucibus quis. Pellentesque et magna ornare, tincidunt ante ac"
//    )
//    ,
//    FeedPostModel(
//        "www.google.com",
//        "Shweta Patil",
//        "Senior Developer",
//        "10 min ago",
//        "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Quisque mollis metus ut ornare ultrices. Quisque fringilla hendrerit ipsum pellentesque viverra. Quisque tincidunt felis vitae turpis accumsan dapibus. Aliquam efficitur pulvinar leo, sit amet ornare elit faucibus quis. Pellentesque et magna ornare, tincidunt ante ac"
//    ),
//    FeedPostModel(
//        "www.google.com",
//        "John Oliver",
//        "Data Analyst",
//        "10 min ago",
//        "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Quisque mollis metus ut ornare ultrices. Quisque fringilla hendrerit ipsum pellentesque viverra. Quisque tincidunt felis vitae turpis accumsan dapibus. Aliquam efficitur pulvinar leo, sit amet ornare elit faucibus quis. Pellentesque et magna ornare, tincidunt ante ac"
//    ),
//    FeedPostModel(
//        "www.google.com",
//        "James Falcon",
//        "UI Designer",
//        "10 min ago",
//        "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Quisque mollis metus ut ornare ultrices. Quisque fringilla hendrerit ipsum pellentesque viverra. Quisque tincidunt felis vitae turpis accumsan dapibus. Aliquam efficitur pulvinar leo, sit amet ornare elit faucibus quis. Pellentesque et magna ornare, tincidunt ante ac"
//    ),
//    FeedPostModel(
//        "www.google.com",
//        "Ankit Ghate",
//        "Senior Developer",
//        "10 min ago",
//        "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Quisque mollis metus ut ornare ultrices. Quisque fringilla hendrerit ipsum pellentesque viverra. Quisque tincidunt felis vitae turpis accumsan dapibus. Aliquam efficitur pulvinar leo, sit amet ornare elit faucibus quis. Pellentesque et magna ornare, tincidunt ante ac"
//    ),
//    FeedPostModel(
//        "www.google.com",
//        "Ankit Ghate",
//        "Senior Developer",
//        "10 min ago",
//        "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Quisque mollis metus ut ornare ultrices. Quisque fringilla hendrerit ipsum pellentesque viverra. Quisque tincidunt felis vitae turpis accumsan dapibus. Aliquam efficitur pulvinar leo, sit amet ornare elit faucibus quis. Pellentesque et magna ornare, tincidunt ante ac"
//    ),
//    FeedPostModel(
//        "www.google.com",
//        "Ankit Ghate",
//        "Senior Developer",
//        "10 min ago",
//        "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Quisque mollis metus ut ornare ultrices. Quisque fringilla hendrerit ipsum pellentesque viverra. Quisque tincidunt felis vitae turpis accumsan dapibus. Aliquam efficitur pulvinar leo, sit amet ornare elit faucibus quis. Pellentesque et magna ornare, tincidunt ante ac"
//    ),
//    FeedPostModel(
//        "www.google.com",
//        "Ankit Ghate",
//        "Senior Developer",
//        "10 min ago",
//        "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Quisque mollis metus ut ornare ultrices. Quisque fringilla hendrerit ipsum pellentesque viverra. Quisque tincidunt felis vitae turpis accumsan dapibus. Aliquam efficitur pulvinar leo, sit amet ornare elit faucibus quis. Pellentesque et magna ornare, tincidunt ante ac"
//    ),
//    FeedPostModel(
//        "www.google.com",
//        "Ankit Ghate",
//        "Senior Developer",
//        "10 min ago",
//        "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Quisque mollis metus ut ornare ultrices. Quisque fringilla hendrerit ipsum pellentesque viverra. Quisque tincidunt felis vitae turpis accumsan dapibus. Aliquam efficitur pulvinar leo, sit amet ornare elit faucibus quis. Pellentesque et magna ornare, tincidunt ante ac"
//    ),
//    FeedPostModel(
//        "www.google.com",
//        "Ankit Ghate",
//        "Senior Developer",
//        "10 min ago",
//        "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Quisque mollis metus ut ornare ultrices. Quisque fringilla hendrerit ipsum pellentesque viverra. Quisque tincidunt felis vitae turpis accumsan dapibus. Aliquam efficitur pulvinar leo, sit amet ornare elit faucibus quis. Pellentesque et magna ornare, tincidunt ante ac"
//    ),
//    FeedPostModel(
//        "www.google.com",
//        "Ankit Ghate",
//        "Senior Developer",
//        "10 min ago",
//        "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Quisque mollis metus ut ornare ultrices. Quisque fringilla hendrerit ipsum pellentesque viverra. Quisque tincidunt felis vitae turpis accumsan dapibus. Aliquam efficitur pulvinar leo, sit amet ornare elit faucibus quis. Pellentesque et magna ornare, tincidunt ante ac"
//    )
// )
