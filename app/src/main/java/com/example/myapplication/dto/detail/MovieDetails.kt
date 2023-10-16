package com.example.myapplication.dto.detail

data class MovieDetails(
    val movieCd:String?,
    val movieNm:String?,
    val movieNmEn:String?,
    val movieNmOg:String?,
    val showTm:String?,
    val prdtYear:String?,
    val openDt:String?,
    val prdtStatNm:String?,
    val typeNm:String?,
    val nations:ArrayList<NationInfo> = ArrayList<NationInfo>(),
    val genres:ArrayList<GenreInfo> = ArrayList<GenreInfo>(),
    val directors:ArrayList<DirectorInfo> = ArrayList<DirectorInfo>(),
    val actors:ArrayList<ActorInfo> = ArrayList<ActorInfo>(),
    val companys:ArrayList<CompanyInfo> = ArrayList<CompanyInfo>(),
    val audits:ArrayList<AuditInfo> = ArrayList<AuditInfo>()
)
